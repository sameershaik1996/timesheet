package us.redshift.timesheet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.TaskCard;
import us.redshift.timesheet.domain.TimeSheet;
import us.redshift.timesheet.service.ITimeSheetService;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("timeSheet/v1/api/")
public class TimeSheetController {

    private final ITimeSheetService timeSheetService;

    public TimeSheetController(ITimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @PostMapping("timeSheet/save")
    public TimeSheet saveTimeSheet(@Valid @RequestBody TimeSheet timeSheet) {
        return timeSheetService.saveTimeSheet(timeSheet);
    }

    @PutMapping("timeSheet/update")
    public TimeSheet updateTimeSheet(@Valid @RequestBody TimeSheet timeSheet) {
        return timeSheetService.updateTimeSheet(timeSheet);
    }

    @GetMapping({"timeSheet/"})
    public List<TimeSheet> getAllTimeSheet() {
        return timeSheetService.getAllTimeSheet();
    }

    @GetMapping("timeSheet/{id}")
    public TimeSheet getTimeSheet(@PathVariable(value = "id") Long id) {
        return timeSheetService.getTimeSheet(id);
    }



}
