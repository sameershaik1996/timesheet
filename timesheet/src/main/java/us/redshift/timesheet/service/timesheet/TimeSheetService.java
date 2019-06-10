package us.redshift.timesheet.service.timesheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.function.Function;
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
        if (!timeSheetRepository.existsById(timeSheet.getId()))
            throw new ResourceNotFoundException("TimeSheet", "Id", timeSheet.getId());
        if (TimeSheetStatus.SUBMITTED.equals(status)) {
            int count = timeSheetRepository.findAllByStatusAndFromDateBefore(TimeSheetStatus.PENDING, timeSheet.getFromDate()).size();
            if (count > 1) {
                LOGGER.info(" previous unSubmitted TimeSheets {}", count);
                throw new ValidationException("Please submit previous TimeSheet (" + count + ")");
            }
        }

        Set<TaskCard> taskCards = new HashSet<>(timeSheet.getTaskCards());
        taskCards.forEach(taskCard -> {
            Task task = taskService.getTaskById(taskCard
                    .getTask().getId());
            Employee employee = task.getEmployees().stream().filter(employee1 -> employee1.getEmployeeId() == taskCard.getEmployeeId()).findFirst().orElse(null);
//          setRole
            taskCard.setRole(employee.getRole());


            TaskCard card = taskCardService.calculateAmount(taskCard);
//            if (!status.equals(TimeSheetStatus.PENDING)) {
//                card = taskCardService.calculateAmount(taskCard);
//            }
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
        timeOffs.forEach(timeOff -> timeSheet.addTimeOff(timeOff));
        timeSheet.setStatus(status);
        TimeSheet SaveTimeSheet = timeSheetRepository.save(timeSheet);
        timeSheet.getTaskCards().forEach(taskCard -> {
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
    public TimeSheet getTimeSheetByWeekNumber(Long employeeId, int year, int weekNumber) {

        EmployeeDto employeeDto = employeeFeignClient.getEmployeeById(employeeId).getBody();
        ZoneId defaultZoneId = ZoneId.of("UTC");
        WeekFields weekFields = WeekFields.of(Locale.ENGLISH);


//      Joining Date
        LocalDate dateOfJoining = LocalDate.parse("2019-03-14");
        int joiningWeekNumber = dateOfJoining.get(weekFields.weekOfWeekBasedYear());
        int joiningYear = dateOfJoining.getYear();

        if (employeeDto.getJoiningDate() != null) {
            dateOfJoining = employeeDto.getJoiningDate().toInstant().atZone(defaultZoneId).toLocalDate();
            joiningWeekNumber = dateOfJoining.get(weekFields.weekOfWeekBasedYear());
            joiningYear = dateOfJoining.getYear();
        }

//        System.out.println(dateOfJoining +"  " + joiningWeekNumber);

//      Current Date
        LocalDate today = LocalDate.now();
        int currentWeekNumber = today.get(weekFields.weekOfWeekBasedYear());
        int currentYear = today.getYear();


//        weekNumber = weekNumber == 0 ? currentWeekNumber : weekNumber;
//        year = year == 0 ? currentYear : year;


        TimeSheet timeSheet = new TimeSheet();
        if (weekNumber != 0 && year != 0) {
            timeSheet = timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumberOrderByTaskCardsAsc(employeeId, year, weekNumber);
            if (timeSheet != null) {
                return timeSheet;
            } else if (timeSheet == null && ((weekNumber <= currentWeekNumber && year <= currentYear) && (weekNumber >= joiningWeekNumber && year >= joiningYear))) {
                TimeSheet newTimeSheet = newTimeSheet(employeeId, weekNumber, year, null);
                timeSheet = timeSheetRepository.save(newTimeSheet);
                System.out.println("testing1234");
            } /*else if (timeSheet == null && weekNumber >= joiningWeekNumber && year >= joiningYear) {
                TimeSheet newTimeSheet = newTimeSheet(employeeId, weekNumber, year, null);
                timeSheet = timeSheetRepository.save(newTimeSheet);
            }*/ else {
                System.out.println("No Entry Found");
                if (weekNumber > currentWeekNumber)
                    throw new ValidationException("No Permission to fill future TimeSheets");
                if (weekNumber < joiningWeekNumber)
                    throw new ValidationException("No Permission to fill TimeSheets before Joining Date");
            }
        } else if (weekNumber == 0) {
            timeSheet = timeSheetRepository.findFirstByEmployeeIdAndStatusOrderByFromDateAsc(employeeId, TimeSheetStatus.PENDING);
            if (timeSheet == null) {
                timeSheet = timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumberOrderByTaskCardsAsc(employeeId, currentYear, currentWeekNumber);
                if (timeSheet == null) {
                    TimeSheet newTimeSheet = newTimeSheet(employeeId, currentWeekNumber, currentYear, null);
                    timeSheet = timeSheetRepository.save(newTimeSheet);
                }
            }
        }
        return timeSheet;
    }

    @Override
    public TimeSheet getTimeSheetByWeekNumberAndEmpId(Long id, Integer weekNumber, Integer year) {
        return timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumberOrderByTaskCardsAsc(id, year, weekNumber);
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


        List<TaskCard> taskCards = new ArrayList<>();
        timeSheetSet.forEach(timeSheet -> {
            timeSheet.getTaskCards().forEach(taskCard -> {
                if (taskCard.getProject().getId() == projectId) {
                    taskCards.add(taskCard);
                }
            });
            timeSheet.setTaskCards(taskCards);
            projectTimeSheet.add(timeSheet);
        });

//        Page<TimeSheet> ProjectTimeSheetPage = new PageImpl<>(projectTimeSheet, new PageRequest(timeSheetPage.getPageable().getPageNumber(), timeSheetPage.getPageable().getPageSize(), timeSheetPage.getPageable().getSort()),
//                projectTimeSheet.size());
//        return ProjectTimeSheetPage;

        Page<TimeSheet> ProjectTimeSheetPage = new Page<TimeSheet>() {
            @Override
            public int getTotalPages() {
                return timeSheetPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return timeSheetPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super TimeSheet, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return timeSheetPage.getNumber();
            }

            @Override
            public int getSize() {
                return timeSheetPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return timeSheetPage.getNumberOfElements();
            }

            @Override
            public List<TimeSheet> getContent() {
                return projectTimeSheet;
            }

            @Override
            public boolean hasContent() {
                return timeSheetPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return timeSheetPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return timeSheetPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return timeSheetPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return timeSheetPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return timeSheetPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return timeSheetPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return timeSheetPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<TimeSheet> iterator() {
                return projectTimeSheet.iterator();
            }
        };

        return ProjectTimeSheetPage;

    }


    private TimeSheet newTimeSheet(Long employeeId, int weekNumber, int year, LocalDate date) {

        ZoneId defaultZoneId = ZoneId.of("UTC");
        if (date == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setWeekDate(year, weekNumber, 2);
            date = calendar.getTime().toInstant().atZone(defaultZoneId).toLocalDate();
            System.out.println(date);
        }


        LocalDate monday = date.with(previousOrSame(MONDAY));
        System.out.println(monday);
        LocalDate sunday = date.with(nextOrSame(SUNDAY));
        TimeSheet newTimeSheet = new TimeSheet();
        newTimeSheet.setFromDate(Date.from(monday.atStartOfDay(defaultZoneId).toInstant()));
        newTimeSheet.setToDate(Date.from(sunday.atStartOfDay(defaultZoneId).toInstant()));
        newTimeSheet.setWeekNumber(weekNumber);
        newTimeSheet.setEmployeeId(employeeId);
        newTimeSheet.setYear(year);
        return newTimeSheet;
    }


}
