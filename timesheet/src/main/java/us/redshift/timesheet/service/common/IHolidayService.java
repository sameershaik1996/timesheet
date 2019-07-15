package us.redshift.timesheet.service.common;

import us.redshift.timesheet.domain.common.HolidayList;

import java.util.ArrayList;
import java.util.List;

public interface IHolidayService {
    List<HolidayList> createHolidayEntry(ArrayList<HolidayList> holidayList);
}
