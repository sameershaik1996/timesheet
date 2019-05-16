package us.redshift.timesheet.service.timesheet;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeOff;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.reposistory.taskcard.TaskCardRepository;
import us.redshift.timesheet.reposistory.timesheet.TimeSheetRepository;
import us.redshift.timesheet.service.task.TaskService;
import us.redshift.timesheet.service.taskcard.TaskCardService;
import us.redshift.timesheet.util.Reusable;

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

    private final TimeSheetRepository timeSheetRepository;
    private final TaskCardService taskCardService;
    private final TaskCardDetailRepository taskCardDetailRepository;
    private final TaskCardRepository taskCardRepository;
    private final TaskService taskService;


    public TimeSheetService(TimeSheetRepository timeSheetRepository, TaskCardService taskCardService, TaskCardDetailRepository taskCardDetailRepository, TaskCardRepository taskCardRepository, TaskService taskService) {
        this.timeSheetRepository = timeSheetRepository;
        this.taskCardService = taskCardService;
        this.taskCardDetailRepository = taskCardDetailRepository;
        this.taskCardRepository = taskCardRepository;
        this.taskService = taskService;
    }


    @Override
    public TimeSheet updateTimeSheet(TimeSheet timeSheet, TimeSheetStatus status) {


        timeSheetRepository.findById(timeSheet.getId()).orElseThrow(() -> new ResourceNotFoundException("TimeSheet", "Id", timeSheet.getId()));
        timeSheet.setStatus(status);
        Set<TaskCard> taskCards = new HashSet<>(timeSheet.getTaskCards());
        taskCards.forEach(taskCard -> {
            TaskCard card = taskCardService.calculateAmount(taskCard);
            timeSheet.addTaskCard(card);
            if (status.equals(TimeSheetStatus.SUBMITTED) || status.equals(TimeSheetStatus.APPROVED)) {
                taskCardDetailRepository.setStatusForTaskCardDetail(status.name(), card.getId());
                if (status.equals(TimeSheetStatus.APPROVED)) {
                    if (taskCard.getType().equals(TaskType.BILLABLE))
                        taskService.updateTask(taskCard.getTask().getId(), taskCard.getHours());
                }
            }
        });

        Set<TimeOff> timeOffs = new HashSet<>(timeSheet.getTimeOffs());

        timeOffs.forEach(timeOff -> timeSheet.addTimeOff(timeOff));
        TimeSheet savedTimeSheet = timeSheetRepository.save(timeSheet);
        if (status.equals(TimeSheetStatus.SUBMITTED) || status.equals(TimeSheetStatus.APPROVED)) {
            taskCardRepository.setStatusForTaskCard(status.name(), timeSheet.getId());

        }

        return savedTimeSheet;
    }

    @Override
    public Set<TimeSheet> getAllTimeSheetByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll(pageable).getContent();
        Set<TimeSheet> timeSheetSet = timeSheetList.stream().collect(Collectors.toCollection(HashSet::new));
        return timeSheetSet;
    }

    @Override
    public TimeSheet getTimeSheet(Long id) {
        return timeSheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TimeSheet", "Id", id));
    }

    @Override
    public TimeSheet getTimeSheetByWeekNumber(Long employeeId, int year, int weekNumber) {


        WeekFields weekFields = WeekFields.of(Locale.ENGLISH);
        LocalDate today = LocalDate.now();
        int currentWeekNumber = today.get(weekFields.weekOfWeekBasedYear());
        int currentYear = today.getYear();
        year = year == 0 ? currentYear : year;
        TimeSheet timeSheet = new TimeSheet();
        if (weekNumber != 0 && year != 0) {
            timeSheet = timeSheetRepository.findTimeSheetByEmployeeIdAndYearAndWeekNumber(employeeId, year, weekNumber);
            if (timeSheet == null && weekNumber == currentWeekNumber && year == currentYear) {
                TimeSheet newTimeSheet = newTimeSheet(employeeId, currentWeekNumber, currentYear, today);
                timeSheet = timeSheetRepository.save(newTimeSheet);
            } else
                throw new ValidationException("No Entry Found");
        } else if (weekNumber == 0) {
            timeSheet = timeSheetRepository.findFirstByEmployeeIdAndStatusOrderByFromDateDesc(employeeId, TimeSheetStatus.PENDING);
            if (timeSheet == null) {
                TimeSheet newTimeSheet = newTimeSheet(employeeId, currentWeekNumber, currentYear, today);
                timeSheet = timeSheetRepository.save(newTimeSheet);
            }
        }
        return timeSheet;
    }


    private TimeSheet newTimeSheet(Long employeeId, int currentWeekNumber, int currentYear, LocalDate today) {
        ZoneId defaultZoneId = ZoneId.of("UTC");
        LocalDate monday = today.with(previousOrSame(MONDAY));
        LocalDate sunday = today.with(nextOrSame(SUNDAY));
        TimeSheet newTimeSheet = new TimeSheet();
        newTimeSheet.setFromDate(Date.from(monday.atStartOfDay(defaultZoneId).toInstant()));
        newTimeSheet.setToDate(Date.from(sunday.atStartOfDay(defaultZoneId).toInstant()));
        newTimeSheet.setWeekNumber(currentWeekNumber);
        newTimeSheet.setEmployeeId(employeeId);
        newTimeSheet.setYear(currentYear);
        return newTimeSheet;
    }


}
