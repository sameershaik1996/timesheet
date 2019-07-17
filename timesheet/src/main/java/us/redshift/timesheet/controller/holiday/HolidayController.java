package us.redshift.timesheet.controller.holiday;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.redshift.timesheet.domain.common.HolidayList;
import us.redshift.timesheet.service.common.IHolidayService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping("timesheet/v1/api/holiday")
public class HolidayController {

    @Autowired
    IHolidayService holidayService;

    @PostMapping("save")
    public ResponseEntity<?> createHoliday(@RequestParam(value = "path") String path) {
        ArrayList<HolidayList> holidaysList = new ArrayList<>();
        try {

            FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                HolidayList holiday = new HolidayList();
                holiday.setDate(currentRow.getCell(0).getDateCellValue());
                holiday.setDescription(currentRow.getCell(1).getStringCellValue());
                holidaysList.add(holiday);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(holidayService.createHolidayEntry(holidaysList), HttpStatus.CREATED);
    }


}
