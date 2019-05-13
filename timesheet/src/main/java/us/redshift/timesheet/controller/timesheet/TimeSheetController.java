package us.redshift.timesheet.controller.timesheet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.service.timesheet.ITimeSheetService;

import javax.validation.Valid;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TimeSheetController {

    private final ITimeSheetService timeSheetService;

    public TimeSheetController(ITimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @PostMapping("timesheet/save")
    public ResponseEntity<?> saveTimeSheet(@Valid @RequestBody TimeSheet timeSheet) {
        return new ResponseEntity<>(timeSheetService.saveTimeSheet(timeSheet), HttpStatus.CREATED);
    }

    @PutMapping("timesheet/update")
    public ResponseEntity<?> updateTimeSheet(@Valid @RequestBody TimeSheet timeSheet, @RequestParam("status") String status) {
        return new ResponseEntity<>(timeSheetService.updateTimeSheet(timeSheet, status), HttpStatus.OK);
    }

    @GetMapping({"timesheet/"})
    public ResponseEntity<?> getAllTimeSheetByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) {
        return new ResponseEntity<>(timeSheetService.getAllTimeSheetByPagination(page, limits, orderBy, fields), HttpStatus.OK);
    }

    @GetMapping("timesheet/{id}")
    public ResponseEntity<?> getTimeSheet(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(timeSheetService.getTimeSheet(id), HttpStatus.OK);
    }
}
