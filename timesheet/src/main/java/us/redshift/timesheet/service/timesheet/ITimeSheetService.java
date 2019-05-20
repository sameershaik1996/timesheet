package us.redshift.timesheet.service.timesheet;


import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.Set;

public interface ITimeSheetService {

    TimeSheet updateTimeSheet(TimeSheet timeSheet, TimeSheetStatus status);


    Set<TimeSheet> getAllTimeSheetByPagination(int page, int limits, String orderBy, String... fields);

    TimeSheet getTimeSheet(Long id);

    TimeSheet getTimeSheetByWeekNumber(Long employeeId, int year, int weekNumber);


    TimeSheet getTimeSheetByWeekNumberAndEmpId(Long id, Integer weekNumber, Integer year);


    TimeSheet cloneTimeSheet(TimeSheet timeSheet);
}
