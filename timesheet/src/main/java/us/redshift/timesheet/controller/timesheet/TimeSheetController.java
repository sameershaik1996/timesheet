package us.redshift.timesheet.controller.timesheet;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TimeSheetAssembler;
import us.redshift.timesheet.assembler.TimeSheetCloneAssembler;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;
import us.redshift.timesheet.dto.timesheet.TimesheetCloneDto;
import us.redshift.timesheet.service.timesheet.ITimeSheetService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Calendar;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TimeSheetController {

    private final ITimeSheetService timeSheetService;
    private final TimeSheetAssembler timeSheetAssembler;
    private final TimeSheetCloneAssembler timeSheetCloneAssembler;
    private final Calendar calendar;

    public TimeSheetController(ITimeSheetService timeSheetService,
                               TimeSheetAssembler timeSheetAssembler,
                               Calendar calendar,
                               TimeSheetCloneAssembler timeSheetCloneAssembler) {
        this.timeSheetService = timeSheetService;
        this.timeSheetAssembler = timeSheetAssembler;
        this.calendar = calendar;
        this.timeSheetCloneAssembler = timeSheetCloneAssembler;
    }


    @PutMapping("timesheet/update")
    public ResponseEntity<?> updateTimeSheet(@Valid @RequestBody TimeSheetDto timeSheetDto, @RequestParam(value = "status", defaultValue = "PENDING") String status) throws ParseException {
        TimeSheet timeSheet = timeSheetAssembler.convertToEntity(timeSheetDto);
        TimeSheet savedTimeSheet = timeSheetService.updateTimeSheet(timeSheet, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(timeSheetAssembler.convertToDto(savedTimeSheet), HttpStatus.OK);
    }


    @GetMapping("timesheet/get/{id}")
    public ResponseEntity<?> getTimeSheet(@PathVariable(value = "id") Long id) throws ParseException {
        TimeSheet timeSheet = timeSheetService.getTimeSheet(id);

        return new ResponseEntity<>(timeSheetAssembler.convertToDto(timeSheet), HttpStatus.OK);
    }


    @GetMapping({"timesheet/get"})
    public ResponseEntity<?> getTimeSheetByWeekNumber(@RequestParam(value = "projectId", required = false, defaultValue = "0") Long projectId, @RequestParam(value = "employeeId", required = false) Long employeeId, @RequestParam(value = "year", defaultValue = "0", required = false) int year, @RequestParam(value = "weekNumber", required = false, defaultValue = "0") int weekNumber, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "0") int limits, @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        if (projectId != 0) {
            Page<TimeSheet> timeSheetPage = timeSheetService.getAllTimeSheetByProjectId(projectId, page, limits, orderBy, fields);
            return new ResponseEntity<>(timeSheetAssembler.convertToPagedDto(timeSheetPage), HttpStatus.OK);
        } else if (employeeId != null) {
            TimeSheet timeSheet = timeSheetService.getTimeSheetByWeekNumber(employeeId, year, weekNumber);
            return new ResponseEntity<>(timeSheetAssembler.convertToDto(timeSheet), HttpStatus.OK);
        } else {
            Page<TimeSheet> timeSheetPage = timeSheetService.getAllTimeSheetByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(timeSheetAssembler.convertToPagedBasicDto(timeSheetPage), HttpStatus.OK);
        }
    }

    @PutMapping("timesheet/clone")
    public ResponseEntity<?> cloneTimesheet(@Valid @RequestBody TimeSheetDto timeSheetDto, @RequestParam(value = "status", defaultValue = "PENDING") String status) throws ParseException {


        if (timeSheetDto.getTaskCards().size() > 0) {
            new ResponseEntity<>("Cannot clone timesheet, entries already exist", HttpStatus.BAD_REQUEST);
        }
        TimeSheet previousTimeSheet = timeSheetService.getTimeSheetByWeekNumberAndEmpId(timeSheetDto.getEmployee().getId(), timeSheetDto.getWeekNumber() - 1, timeSheetDto.getYear());

        TimesheetCloneDto timesheetCloneDto = timeSheetCloneAssembler.convertToCloneDto(previousTimeSheet);
        timesheetCloneDto.setId(timeSheetDto.getId());
        timesheetCloneDto.setWeekNumber(timeSheetDto.getWeekNumber());
        TimeSheet timeSheet = timeSheetCloneAssembler.convertCloneToEntity(timesheetCloneDto);

        return new ResponseEntity<>(timeSheetAssembler.convertToDto(timeSheetService.cloneTimeSheet(timeSheet)), HttpStatus.OK);
    }

}
