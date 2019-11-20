package us.redshift.timesheet.service.timesheet;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Employee;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeOff;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;
import us.redshift.timesheet.reposistory.timesheet.TimeSheetRepository;
import us.redshift.timesheet.service.task.ITaskService;
import us.redshift.timesheet.service.taskcard.ITaskCardDetailService;
import us.redshift.timesheet.service.taskcard.ITaskCardService;
import us.redshift.timesheet.util.Reusable;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@Service
public class TimeSheetService implements ITimeSheetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeSheetService.class);

    private final TimeSheetRepository timeSheetRepository;
    private final ITaskCardService taskCardService;
    private final ITaskCardDetailService taskCardDetailService;
    private final ITaskService taskService;
    private final Calendar calendar;
    private final EmployeeFeignClient employeeFeignClient;

    public TimeSheetService(TimeSheetRepository timeSheetRepository,
                            @Lazy ITaskCardService taskCardService,
                            @Lazy ITaskCardDetailService taskCardDetailService,
                            @Lazy ITaskService taskService,
                            Calendar calendar,
                            @Lazy EmployeeFeignClient employeeFeignClient) {
        this.timeSheetRepository = timeSheetRepository;
        this.taskCardService = taskCardService;
        this.taskCardDetailService = taskCardDetailService;
        this.taskService = taskService;
        this.calendar = calendar;
        this.employeeFeignClient = employeeFeignClient;
    }


    @Override
    public TimeSheet updateTimeSheet(TimeSheet timeSheet, TimeSheetStatus status) {
        /*if (!timeSheetRepository.existsById(timeSheet.getId()))
            throw new ResourceNotFoundException("TimeSheet", "Id", timeSheet.getId());*/
        if (TimeSheetStatus.SUBMITTED.equals(status)) {
            int count = timeSheetRepository.findAllByStatusAndFromDateLessThanAndEmployeeId(TimeSheetStatus.PENDING, timeSheet.getFromDate(), timeSheet.getEmployeeId()).size();
            if (count > 0) {
                LOGGER.info(" previous unSubmitted TimeSheets {}", count);
                throw new ValidationException("Please submit previous TimeSheet (" + count + ")");
            }
        }

        Set<TaskCard> taskCards = new HashSet<>(timeSheet.getTaskCards());

        if (taskCards.size() == 0)
            throw new ValidationException("Unable to Submit TimeSheet without Entries");

        taskCards.forEach(taskCard -> {

            if (taskCard.getTask() == null)
                throw new ValidationException("Task cannot be empty");
            if (taskCard.getProject() == null)
                throw new ValidationException("Project cannot be empty");
            if (taskCard.getLocation() == null)
                throw new ValidationException("Location cannot be empty");
            if (taskCard.getType() == null)
                throw new ValidationException("TaskType cannot be empty");


//          UpdateActual Dates
            taskCardService.UpdateActualDates(taskCard.getProject().getId(), taskCard.getTask().getId());


            EmployeeDto employeeDto = employeeFeignClient.getEmployeeById(taskCard.getEmployeeId()).getBody();


//      Set Designation Id
            if (taskCard.getDesignationId() == null)
                taskCard.setDesignationId(employeeDto.getDesignation().getId());


            Task task = taskService.getTaskById(taskCard
                    .getTask().getId());

//       Set ApproverId
            if (taskCard.getApproverId() == null && employeeDto.getReportingManager() != null)
                taskCard.setApproverId(task.getProject().getManagerId());

            Employee employee = task.getEmployees().stream().filter(employee1 -> employee1.getEmployeeId() == taskCard.getEmployeeId()).findFirst().orElse(null);
//          setRole
            taskCard.setRole(employee.getRole());

            if (taskCard.getTaskCardDetails().size() == 0)
                throw new ValidationException("Unable to Submit TimeSheet without Entries");


            taskCard.getTaskCardDetails().stream().forEach(taskCardDetail -> {
                taskCardService.checkDates(taskCardDetail, employeeDto.getJoiningDate());
                taskCard.add(taskCardDetail);
            });

            TaskCard card = taskCard;
            if (status.equals(TimeSheetStatus.SUBMITTED) || status.equals(TimeSheetStatus.APPROVED)) {
                card = taskCardService.calculateAmount(taskCard);
            }
            card.setStatus(status);
            timeSheet.addTaskCard(card);
            if (status.equals(TimeSheetStatus.APPROVED)) {
                if (card.getType().equals(TaskType.BILLABLE)) {
                    LOGGER.info(" UpdateTimeSheet updateTask Hour With {}", card.getHours());
//                    Update usedHour
                    taskService.updateTaskHours(card.getTask().getId(), card.getHours());
                }
            }
        });
        Set<TimeOff> timeOffs = new HashSet<>(timeSheet.getTimeOffs());

        timeOffs.forEach( timeOff ->{
                if(status.equals(TimeSheetStatus.SUBMITTED)){
                    timeOff.setStatus(status);
                }
                timeOff.getDates().stream().forEach(timeOffDate -> {
                    timeOff.add(timeOffDate);
                });
                timeSheet.addTimeOff(timeOff);
        });

        timeSheet.setStatus(status);

        TimeSheet SaveTimeSheet = timeSheetRepository.save(timeSheet);
        SaveTimeSheet.getTaskCards().forEach(taskCard -> {
            LOGGER.info("UpdateTimeSheet TaskCardDetails Status Updated {}", taskCardDetailService.setStatusForTaskCardDetailByTaskCardId(status.name(), taskCard.getId()));
        });

        return SaveTimeSheet;
    }

    @Override
    public Page<TimeSheet> getAllTimeSheetByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
//        List<TimeSheet> timeSheetList = timeSheetRepository.findAll(pageable).getContent();
//        Set<TimeSheet> timeSheetSet = timeSheetList.stream().collect(Collectors.toCollection(HashSet::new));
        return timeSheetRepository.findAll(pageable);
    }

    @Override
    public TimeSheet getTimeSheet(Long id) {
        return timeSheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TimeSheet", "Id", id));
    }

    @Override
    public List<TimeSheet> getTimeSheetByWeekNumber(Long employeeId, int year, int weekNumber) {

        EmployeeDto employeeDto = employeeFeignClient.getEmployeeById(employeeId).getBody();
        ZoneId defaultZoneId = ZoneId.of("UTC");
        WeekFields weekFields = WeekFields.of(Locale.ENGLISH);


//      Joining Date
//        TODO Remove HardCode

        LocalDate dateOfJoining;
        int joiningWeekNumber=0 ;
        int joiningYear =0;
        if (employeeDto.getJoiningDate() != null) {
            dateOfJoining = employeeDto.getJoiningDate().toInstant().atZone(defaultZoneId).toLocalDate();
            joiningWeekNumber = dateOfJoining.get(weekFields.weekOfWeekBasedYear());
            joiningYear = dateOfJoining.getYear();
        }

//        ////////System.out.println(dateOfJoining +"  " + joiningWeekNumber);

//      Current Date
        LocalDate today = LocalDate.now();
        int currentWeekNumber = today.get(weekFields.weekOfWeekBasedYear());
        int currentYear = today.getYear();


//        weekNumber = weekNumber == 0 ? currentWeekNumber : weekNumber;
//        year = year == 0 ? currentYear : year;


        List<TimeSheet> timeSheet = new ArrayList<TimeSheet>();
        if (weekNumber != 0 && year != 0) {
            timeSheet = timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumber(employeeId, year, weekNumber);
            if (timeSheet.size()>0) {
                return timeSheet;
            } else {
                ////////System.out.println("No Entry Found");
                if (weekNumber > currentWeekNumber)
                    throw new ValidationException("No Permission to fill future TimeSheets");
                if (weekNumber < joiningWeekNumber)
                    throw new ValidationException("No Permission to fill TimeSheets before Joining Date");
            }
        } else if (weekNumber == 0) {
            TimeSheet ts=timeSheetRepository.findFirstByEmployeeIdAndStatusOrderByFromDateAsc(employeeId,TimeSheetStatus.PENDING);
            if(ts!=null){
                timeSheet = timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumber(employeeId,ts.getYear(),ts.getWeekNumber());
            }else
            {
                timeSheet = timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumber(employeeId,currentYear,currentWeekNumber);
            }



        }
        return timeSheet;
    }

    @Override
    public List<TimeSheet> getTimeSheetByWeekNumberAndEmpId(Long id, Integer weekNumber, Integer year) {
        return timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumber(id, year, weekNumber);
    }

    @Override
    public TimeSheet cloneTimeSheet(TimeSheet timeSheet) {
        List<TaskCard> taskCards = timeSheet.getTaskCards();


        taskCards.forEach(taskCard ->
        {
            List<TaskCardDetail> cardDetails = taskCard.getTaskCardDetails();
            cardDetails.forEach(taskCardDetail ->
            {
                calendar.setTime(taskCardDetail.getDate());
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                taskCardDetail.setDate(calendar.getTime());
                taskCard.add(taskCardDetail);

            });
            taskCard.setTaskCardDetails(cardDetails);
            timeSheet.addTaskCard(taskCard);
        });
        timeSheet.setTaskCards(taskCards);

        return timeSheetRepository.save(timeSheet);
    }

    @Override
    public Integer setStatusForTimeSheet(String status, Long timeSheetId) {
        return timeSheetRepository.setStatusForTimeSheet(status, timeSheetId);
    }

    @Override
    public Page<TimeSheet> getAllTimeSheetByProjectId(Long projectId, int page, int limits, String orderBy, String... fields) {

        Set<TaskCard> taskCardSet = new HashSet<>(taskCardService.getAllTaskCardByProject(projectId));

        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        Page<TimeSheet> timeSheetPage = timeSheetRepository.findAllByTaskCardsInAndStatusNotLikeOrderByFromDateAsc(taskCardSet, TimeSheetStatus.PENDING, pageable);

        Set<TimeSheet> timeSheetSet = timeSheetPage.getContent().stream().collect(Collectors.toCollection(LinkedHashSet::new));
        List<TimeSheet> projectTimeSheet = new ArrayList<>();


        timeSheetSet.forEach(timeSheet -> {
            List<TaskCard> taskCards = new ArrayList<>();
            timeSheet.getTaskCards().forEach(taskCard -> {
                if (taskCard.getProject().getId() == projectId&&!taskCard.getStatus().equals(TimeSheetStatus.PENDING)) {
                    taskCards.add(taskCard);
                }
            });
            timeSheet.setTaskCards(taskCards);
            projectTimeSheet.add(timeSheet);
        });
        return Reusable.getPaginated(timeSheetPage, projectTimeSheet);

    }


    private TimeSheet newTimeSheet(Long employeeId, int weekNumber, int year, LocalDate date) {

        ZoneId defaultZoneId = ZoneId.of("UTC");
        if (date == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setWeekDate(year, weekNumber, Calendar.MONDAY);
            date = calendar.getTime().toInstant().atZone(defaultZoneId).toLocalDate();
        }
        LocalDate startDate;
        LocalDate endDate;
        List<TimeSheet> timeSheet= timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumberAndStatusOrderByTaskCardsAsc(employeeId, year, weekNumber,TimeSheetStatus.SUBMITTED);
        if(timeSheet.size()>0){
            startDate= LocalDate.now();
        }
        else
            startDate = date.with((previousOrSame(MONDAY)));
        ////////System.out.println(monday);
        LocalDate sunday = date.with(nextOrSame(SUNDAY));
        ////////System.out.println(sunday);
        TimeSheet newTimeSheet = new TimeSheet();

//        newTimeSheet.setFromDate(Date.from(monday.atZone(defaultZoneId).toInstant()));
        newTimeSheet.setFromDate(Date.from(startDate.atStartOfDay(defaultZoneId).toInstant()));

        ////////System.out.println(newTimeSheet.getFromDate());
        newTimeSheet.setToDate(Date.from(sunday.atStartOfDay(defaultZoneId).toInstant()));
        newTimeSheet.setWeekNumber(weekNumber);
        newTimeSheet.setEmployeeId(employeeId);
        newTimeSheet.setYear(year);
        return newTimeSheet;
    }


}
