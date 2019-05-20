package us.redshift.timesheet.controller.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.client.Industry;
import us.redshift.timesheet.service.client.IIndustryService;

import javax.validation.Valid;

@RestController
@RequestMapping("timesheet/v1/api/")
public class IndustryController {


    private final IIndustryService industryService;

    public IndustryController(IIndustryService industryService) {
        this.industryService = industryService;
    }


    @PostMapping("client/industry/save")
    public ResponseEntity<?> createIndustry(@Valid @RequestBody Industry industry) {

        return new ResponseEntity<>(industryService.saveIndustry(industry), HttpStatus.CREATED);
    }

    @PutMapping("client/industry/update")
    public ResponseEntity<?> updateIndustry(@Valid @RequestBody Industry industry) {

        return new ResponseEntity<>(industryService.updateIndustry(industry), HttpStatus.CREATED);
    }

    @GetMapping("client/industry/get")
    public ResponseEntity<?> getAllIndustries() {

        return new ResponseEntity<>(industryService.getAllIndustries(), HttpStatus.OK);
    }

    @GetMapping("client/industry/get/{id}")
    public ResponseEntity<?> getIndustryById(@PathVariable Long id) {

        return new ResponseEntity<>(industryService.getIndustryById(id), HttpStatus.OK);
    }


}
