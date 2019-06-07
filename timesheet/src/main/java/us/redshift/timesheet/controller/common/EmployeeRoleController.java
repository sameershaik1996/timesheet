package us.redshift.timesheet.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.EmployeeRole;
import us.redshift.timesheet.service.common.EmployeeRoleService;
import us.redshift.timesheet.service.common.IEmployeeRoleService;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("timesheet/v1/api/")
public class EmployeeRoleController {

    private final IEmployeeRoleService employeeRoleService;

    public EmployeeRoleController(EmployeeRoleService employeeRoleService) {
        this.employeeRoleService = employeeRoleService;
    }

    @PostMapping("employeerole/save")
    public ResponseEntity<?> saveEmployeeRole(@Valid @RequestBody EmployeeRole role) throws ParseException {

        return new ResponseEntity<>(employeeRoleService.saveEmployeeRole(role), HttpStatus.CREATED);
    }

    @GetMapping("employeerole/update")
    public ResponseEntity<?> UpdateEmployeeRole(@Valid @RequestBody EmployeeRole role) throws ParseException {

        return new ResponseEntity<>(employeeRoleService.UpdateEmployeeRole(role), HttpStatus.CREATED);
    }

    @GetMapping("employeerole/get")
    public ResponseEntity<?> getEmployeeRole(@RequestParam(value = "id", required = false) Long id) throws ParseException {
        if (id != null)
            return new ResponseEntity<>(employeeRoleService.getEmployeeRoleById(id), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(employeeRoleService.getAllEmployeeRole(), HttpStatus.CREATED);
    }

}
