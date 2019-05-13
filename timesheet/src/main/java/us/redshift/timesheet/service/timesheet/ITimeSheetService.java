package us.redshift.timesheet.service.timesheet;


import us.redshift.timesheet.domain.timesheet.TimeSheet;

import java.util.Set;

public interface ITimeSheetService {

    TimeSheet saveTimeSheet(TimeSheet timeSheet);

    TimeSheet updateTimeSheet(TimeSheet timeSheet, String status);

    Set<TimeSheet> getAllTimeSheetByPagination(int page, int limits, String orderBy, String... fields);

    TimeSheet getTimeSheet(Long id);
}
