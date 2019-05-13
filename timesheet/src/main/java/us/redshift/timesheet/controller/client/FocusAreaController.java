package us.redshift.timesheet.controller.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.client.FocusArea;
import us.redshift.timesheet.service.client.IFocusAreaService;

import javax.validation.Valid;

@RestController
@RequestMapping("timesheet/v1/api/")
public class FocusAreaController {


    private final IFocusAreaService focusAreaService;

    public FocusAreaController(IFocusAreaService focusAreaService) {
        this.focusAreaService = focusAreaService;
    }


    @PostMapping("focusarea/save")
    public ResponseEntity<?> createFocusArea(@Valid @RequestBody FocusArea focusArea) {

        return new ResponseEntity<>(focusAreaService.saveFocusArea(focusArea), HttpStatus.CREATED);
    }

    @PutMapping("focusarea/update")
    public ResponseEntity<?> updateFocusArea(@Valid @RequestBody FocusArea focusArea) {

        return new ResponseEntity<>(focusAreaService.updateFocusArea(focusArea), HttpStatus.CREATED);
    }

    @GetMapping("focusareas")
    public ResponseEntity<?> getAllFocusAreas() {

        return new ResponseEntity<>(focusAreaService.getAllFocusAreas(), HttpStatus.OK);
    }

    @GetMapping("focusarea/{id}")
    public ResponseEntity<?> getFocusAreaById(@PathVariable Long id) {

        return new ResponseEntity<>(focusAreaService.getFocusAreaById(id), HttpStatus.OK);
    }


}
