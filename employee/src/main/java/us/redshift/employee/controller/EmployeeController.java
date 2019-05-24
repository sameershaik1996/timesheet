package us.redshift.employee.controller;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import us.redshift.filter.model.UserDetails;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employee)throws ConstraintViolationException,DataIntegrityViolationException {

        Employee newEmp = mapper.map(employee,Employee.class);
        Employee emp = employeeService.createEmployee(newEmp);
        userClient.createUser(generateUserDto(emp),employee.getRoleId());
        return new ResponseEntity<>(emp, HttpStatus.CREATED);//send mail


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



    @GetMapping("get/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id,HttpServletRequest req)throws NoSuchElementException, EntityNotFoundException
    {
        System.out.println("details:"+req.getHeader("userDetails"));

        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);

    }

    @GetMapping("get")
    public ResponseEntity<?> getAllEmployee(@RequestParam(value="id",required = false) List<Long> id,@RequestParam(value="roleName",required = false)String roleName)
    {
        if(id!=null)
            return new ResponseEntity<>(employeeService.getEmployeeByIds(id), HttpStatus.OK);
        else if(roleName!=null)
        {
            List<UserDetails> ud=(List<UserDetails>) userClient.getUser(roleName);
            List<Long> empIds=new ArrayList<>();
            ud.forEach(u -> {
                if(u.getEmployeeId()!=0)
                    empIds.add(u.getEmployeeId());
            });
            return new ResponseEntity<>(employeeService.getEmployeeByIds(empIds), HttpStatus.OK);

        }


        else
            return new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK);


    }

    @GetMapping("exists/{id}")
    public ResponseEntity<?> checkIfEmployeeExists(@PathVariable Long id)
    {


        return new ResponseEntity<>(employeeService.checkIfEmployeeExists(id),HttpStatus.OK);
    }

}

