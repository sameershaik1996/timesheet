package us.redshift.timesheet.controller.timesheet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TimeSheetAssembler;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;
import us.redshift.timesheet.service.timesheet.ITimeSheetService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TimeSheetController {

    private final ITimeSheetService timeSheetService;
    private final TimeSheetAssembler timeSheetAssembler;

    public TimeSheetController(ITimeSheetService timeSheetService, TimeSheetAssembler timeSheetAssembler) {
        this.timeSheetService = timeSheetService;
        this.timeSheetAssembler = timeSheetAssembler;
    }


    @PutMapping("timesheet/update")
    public ResponseEntity<?> updateTimeSheet(@Valid @RequestBody TimeSheetDto timeSheetDto, @RequestParam(value = "status", defaultValue = "PENDING") String status) throws ParseException {
        TimeSheet timeSheet = timeSheetAssembler.convertToEntity(timeSheetDto);
        TimeSheet savedTimeSheet = timeSheetService.updateTimeSheet(timeSheet, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(timeSheetAssembler.convertToDto(savedTimeSheet), HttpStatus.OK);
    }

    @GetMapping({"timesheet"})
    public ResponseEntity<?> getAllTimeSheetByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        Set<TimeSheet> timeSheets = timeSheetService.getAllTimeSheetByPagination(page, limits, orderBy, fields);
        return new ResponseEntity<>(timeSheetAssembler.convertToDto(timeSheets), HttpStatus.OK);
    }

    @GetMapping("timesheet/get/{id}")
    public ResponseEntity<?> getTimeSheet(@PathVariable(value = "id") Long id) throws ParseException {
        TimeSheet timeSheet = timeSheetService.getTimeSheet(id);

        return new ResponseEntity<>(timeSheetAssembler.convertToDto(timeSheet), HttpStatus.OK);
    }


    @GetMapping({"timesheet/get"})
    public ResponseEntity<?> getTimeSheetByWeekNumber(@RequestParam(value = "projectId", required = false, defaultValue = "0") Long projectId, @RequestParam(value = "employeeId", required = false) Long employeeId, @RequestParam(value = "year", defaultValue = "0", required = false) int year, @RequestParam(value = "weekNumber", required = false, defaultValue = "0") int weekNumber) throws ParseException {
        if (projectId != 0) {
            Set<TimeSheet> timeSheetSet = timeSheetService.getAllTimeSheetByProjectId(projectId);
            return new ResponseEntity<>(timeSheetAssembler.convertToDto(timeSheetSet), HttpStatus.OK);
        } else {
            TimeSheet timeSheet = timeSheetService.getTimeSheetByWeekNumber(employeeId, year, weekNumber);
            return new ResponseEntity<>(timeSheetAssembler.convertToDto(timeSheet), HttpStatus.OK);
        }
    }
}
