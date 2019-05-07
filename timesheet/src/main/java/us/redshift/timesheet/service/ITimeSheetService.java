package us.redshift.timesheet.service;


import us.redshift.timesheet.domain.TimeSheet;

import java.util.List;

public interface ITimeSheetService {

    TimeSheet saveTimeSheet(TimeSheet timeSheet);

    TimeSheet updateTimeSheet(TimeSheet timeSheet);

    List<TimeSheet> getAllTimeSheet();

    TimeSheet getTimeSheet(Long id);
}
