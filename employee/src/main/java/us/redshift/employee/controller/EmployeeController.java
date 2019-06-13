package us.redshift.employee.controller;


import com.sun.org.apache.xpath.internal.operations.Bool;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.HttpClientErrorException;
import us.redshift.employee.assembler.EmployeeAssembler;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.Skill;
import us.redshift.employee.domain.common.EmployeeStatus;
import us.redshift.employee.dto.EmployeeDto;
import us.redshift.employee.dto.SignUpDto;
import us.redshift.employee.exception.BadRequestException;
import us.redshift.employee.feignclient.IUserClient;
import us.redshift.employee.helper.IDTOHelper;
import us.redshift.employee.repository.EmployeeRespository;
import us.redshift.employee.service.IEmployeeService;
import us.redshift.filter.model.UserDetails;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("employee/v1/api/employee")
public class EmployeeController {

    @Autowired
    ModelMapper mapper;
    @Autowired
    EmployeeAssembler employeeAssembler;

    @Autowired
    IDTOHelper dtoHelper;

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IUserClient userClient;

    @PostMapping("/save")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employee) throws ConstraintViolationException, DataIntegrityViolationException {


        Employee emp = employeeService.createEmployee(mapper.map(employee, Employee.class));
        Boolean status = emp.getStatus().equals(EmployeeStatus.ACTIVE) ? true : false;
        userClient.createUser(generateUserDto(emp), employee.getRole().getId(), status);
        EmployeeDto empDto = employeeAssembler.convertToDto(emp);
        return new ResponseEntity<>(empDto, HttpStatus.CREATED);//send mail


    }

    private SignUpDto generateUserDto(Employee emp) {
        SignUpDto userDto = new SignUpDto();
        userDto.setEmployeeId(emp.getId());
        userDto.setEmailId(emp.getEmailId());
        int userNameIndex = emp.getEmailId().indexOf("@");
        userDto.setUserName(emp.getEmailId().substring(0, userNameIndex));
        userDto.setPassword("password123");
        return userDto;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDto employee,
                                            @RequestParam(value = "empId", required = false) List<Long> empIds,
                                            @RequestParam(value = "status", required = false) EmployeeStatus status
    ) {

        Boolean empStatus = true;
        if (empIds != null && status != null) {
            empStatus = status.equals(EmployeeStatus.ACTIVE) ? true : false;
            //System.out.println(status);
            employeeService.setStatusForEmployee(status, empIds);
            userClient.updateUser(empIds, empStatus);
            return new ResponseEntity<>(employeeService.getEmployeeByIds(empIds), HttpStatus.OK);
        }


        if (employee.getId() == null) {
            return new ResponseEntity<>(new BadRequestException("id cannot be null"), HttpStatus.BAD_REQUEST);
        }

        Employee currentEmployee = employeeService.getEmployeeById(employee.getId());

        mapper.map(employee, currentEmployee);

        EmployeeDto empDto = employeeAssembler.convertToDto(employeeService.updateEmployee(currentEmployee));
        empStatus = empDto.getStatus().equals(EmployeeStatus.ACTIVE) ? true : false;
        userClient.updateUser(empDto.getId(), empStatus, employee.getRole());

        return new ResponseEntity<>(empDto, HttpStatus.CREATED);

    }


    @GetMapping("get/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) throws NoSuchElementException, EntityNotFoundException, HttpClientErrorException {

        EmployeeDto empDto = employeeAssembler.convertToDto(employeeService.getEmployeeById(id));
        return new ResponseEntity<>(empDto, HttpStatus.OK);

    }

    @GetMapping("get")
    public ResponseEntity<?> getAllEmployee(@RequestParam(value = "id", required = false) List<Long> id,
                                            @RequestParam(value = "roleName", required = false) String roleName,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "limits", defaultValue = "0") int limits,
                                            @RequestParam(value = "orderBy", required = false) String orderBy,
                                            @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        //System.out.println(id);
        if (id != null) {
            return new ResponseEntity<>(employeeService.getEmployeeByIds(id), HttpStatus.OK);
        } else if (roleName != null) {
            List<UserDetails> ud = (List<UserDetails>) userClient.getUser(roleName);
            List<Long> empIds = new ArrayList<>();
            ud.forEach(u -> {
                if (u.getEmployeeId() != 0)
                    empIds.add(u.getEmployeeId());
            });
            return new ResponseEntity<>(employeeService.getEmployeeByIds(empIds), HttpStatus.OK);

        } else {
            //System.out.println("gdshvdbnbv");
            return new ResponseEntity<>(employeeService.getAllEmployee(page, limits, orderBy, fields), HttpStatus.OK);
        }


    }

    @GetMapping("exists/{id}")
    public ResponseEntity<?> checkIfEmployeeExists(@PathVariable Long id) {


        return new ResponseEntity<>(employeeService.checkIfEmployeeExists(id), HttpStatus.OK);
    }

}

