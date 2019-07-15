package us.redshift.timesheet.controller.holiday;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.redshift.timesheet.domain.common.HolidayList;
import us.redshift.timesheet.service.common.IHolidayService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/holiday")
public class HolidayController {

    @Autowired
    IHolidayService holidayService;
    @PostMapping("save")
    public ResponseEntity<?> createHoliday(ArrayList<HolidayList> holidayList){
        System.out.println(holidayList.get(0).getDate());
        return new ResponseEntity<>(holidayService.createHolidayEntry(holidayList), HttpStatus.CREATED);
    }





}
