package us.redshift.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.employee.domain.Designation;
import us.redshift.employee.dto.DesignationDto;
import us.redshift.employee.dto.EmployeeDto;
import us.redshift.employee.repository.DesignationRepository;
import us.redshift.employee.service.IDesignationService;
import us.redshift.employee.util.DTO;

import javax.validation.Valid;

@RestController
@RequestMapping("employee/v1/api/designation")
public class DesignationController {
    @Autowired
    DesignationRepository designationRepository;
    @Autowired
    IDesignationService designationService;

    @PostMapping("/save")
    public ResponseEntity<?> createDesignation(@Valid @RequestBody Designation designation){
        return  new ResponseEntity<>(designationService.createDesignation(designation), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDesignation(@Valid @RequestBody Designation designation){
        return new ResponseEntity<>(designationService.updateDesignation(designation),HttpStatus.CREATED);
    }

    @GetMapping("get")
    public ResponseEntity<?> getAllDesignation(){
        return  new ResponseEntity<>(designationService.getAllDesignation(),HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getDestinationById(@PathVariable Long id){
        return  new ResponseEntity<>(designationService.getDesignationById(id),HttpStatus.OK);
    }


}
