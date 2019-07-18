package us.redshift.timesheet.service.timesheet;


import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.List;

public interface ITimeSheetService {

    TimeSheet updateTimeSheet(TimeSheet timeSheet, TimeSheetStatus status);


    Page<TimeSheet> getAllTimeSheetByPagination(int page, int limits, String orderBy, String... fields);

    TimeSheet getTimeSheet(Long id);

    List<TimeSheet> getTimeSheetByWeekNumber(Long employeeId, int year, int weekNumber);

    Page<TimeSheet> getAllTimeSheetByProjectId(Long projectId, int page, int limits, String orderBy, String... fields);


    List<TimeSheet> getTimeSheetByWeekNumberAndEmpId(Long id, Integer weekNumber, Integer year);


    TimeSheet cloneTimeSheet(TimeSheet timeSheet);

    Integer setStatusForTimeSheet(String status, Long timeSheetId);
}
