package us.redshift.timesheet.service;


import us.redshift.timesheet.domain.TimeSheet;

import java.util.List;

public interface ITimeSheetService {

    TimeSheet saveTimeSheet(TimeSheet timeSheet);

    TimeSheet updateTimeSheet(TimeSheet timeSheet, String status);

    List<TimeSheet> getAllTimeSheetByPagination(int page, int limits, String orderBy, String... fields);

    TimeSheet getTimeSheet(Long id);
}
