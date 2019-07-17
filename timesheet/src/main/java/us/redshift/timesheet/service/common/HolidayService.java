package us.redshift.timesheet.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.common.HolidayList;
import us.redshift.timesheet.reposistory.common.HolidayRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService implements IHolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Override
    public List<HolidayList> createHolidayEntry(ArrayList<HolidayList> holidayList) {
        return holidayRepository.saveAll(holidayList);
    }

    @Override
    public List<HolidayList> getHolidayList() {
        return holidayRepository.findAll();
    }
}
