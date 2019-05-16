package us.redshift.employee.controller;


import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.Skill;
import us.redshift.employee.dto.EmployeeDto;
import us.redshift.employee.dto.SignUpDto;
import us.redshift.employee.exception.BadRequestException;
import us.redshift.employee.feignclient.IUserClient;
import us.redshift.employee.helper.IDTOHelper;
import us.redshift.employee.repository.EmployeeRespository;
import us.redshift.employee.service.IEmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employee/v1/api/employee")
public class EmployeeController {

    @Autowired
    ModelMapper mapper;
    @Autowired
    EmployeeRespository employeeRespository;

    @Autowired
    IDTOHelper dtoHelper;

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IUserClient userClient;

    @PostMapping("/save")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee){
    try {
        Employee emp = employeeService.createEmployee(employee);
        userClient.createUser(generateUserDto(emp));
        return new ResponseEntity<>(emp, HttpStatus.CREATED);//send mail
    }
    catch (Exception e){
        e.printStackTrace();
        return new ResponseEntity<>("try again later", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    private SignUpDto generateUserDto(Employee emp) {
        SignUpDto userDto=new SignUpDto();
        userDto.setEmployeeId(emp.getId());
        userDto.setEmailId(emp.getEmailId());
        int userNameIndex= emp.getEmailId().indexOf("@");
        userDto.setUserName(emp.getEmailId().substring(0,userNameIndex));
        userDto.setPassword("password123");
        return userDto;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody Employee employee){

        if(employee.getId()==null){
            return new ResponseEntity<>(new BadRequestException("id cannot be null"),HttpStatus.BAD_REQUEST);
        }

        Employee currentEmployee =employeeService.getEmployeeById(employee.getId());

        mapper.map(employee,currentEmployee);

        return new ResponseEntity<>(employeeService.updateEmployee(currentEmployee), HttpStatus.CREATED);

    }

    @GetMapping("ids")
    public ResponseEntity<?> getEmployeeByIds(@RequestParam(value="id",required = false) List<Long> id)
    {
        if(id==null){
            return new ResponseEntity<>(new ArrayList<Long>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(employeeService.getEmployeeByIds(id), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id)
    {

        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<?> getAllEmployee(HttpServletRequest request)
    {
        System.out.println("asdasd:"+request.getAttribute("details"));

        return new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK);

    }

    @GetMapping("exists/{id}")
    public ResponseEntity<?> checkIfEmployeeExists(@PathVariable Long id)
    {


        return new ResponseEntity<>(employeeService.checkIfEmployeeExists(id),HttpStatus.OK);
    }

}

