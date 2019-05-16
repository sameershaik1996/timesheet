package us.redshift.timesheet.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.common.Location;
import us.redshift.timesheet.reposistory.common.LocationRepository;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("timesheet/v1/api/")
public class LocationController {


    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @PostMapping("location/save")
    public ResponseEntity<?> saveLocation(@Valid @RequestBody Location location) throws ParseException {

        return new ResponseEntity<>(locationRepository.save(location), HttpStatus.CREATED);
    }

    @GetMapping("location/get")
    public ResponseEntity<?> getLocation() throws ParseException {

        return new ResponseEntity<>(locationRepository.findAll(), HttpStatus.CREATED);
    }


}
