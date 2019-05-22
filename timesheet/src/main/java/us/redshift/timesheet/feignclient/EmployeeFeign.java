package us.redshift.timesheet.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import us.redshift.timesheet.dto.common.DesignationDto;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.SkillDto;

import java.util.Set;
import java.util.stream.Collectors;

@FeignClient(url = "${zuul.employee.url}", value = "Employee-Service", fallback = EmployeeFeignFallback.class)
public interface EmployeeFeign {

    @GetMapping("/employee/v1/api/employee/get/{id}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id);

    @GetMapping("/employee/v1/api/employee/get")
    ResponseEntity<Set<EmployeeDto>> getAllEmployeeByIds(@RequestParam("id") Set<Long> ids);

    @GetMapping("/employee/v1/api/skill/get")
    ResponseEntity<Set<SkillDto>> getAllSkillsByIds(@RequestParam("id") Set<Long> ids);

    @GetMapping("/employee/v1/api/skill/get/{id}")
    ResponseEntity<SkillDto> getSkillById(@PathVariable("id") Long id);

    @GetMapping("/employee/v1/api/designation/get/{id}")
    ResponseEntity<DesignationDto> getDesignationById(@PathVariable("id") Long id);

    @GetMapping("/employee/v1/api/skill/get")
    ResponseEntity<Set<SkillDto>> getAllSkillByEmployeeIds(@RequestParam("empId") Set<Long> ids);

}

@Component
class EmployeeFeignFallback implements EmployeeFeign {
    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {
        EmployeeDto dto = new EmployeeDto(id, "NAN", "NAN", "NAN", "NAN");
        System.out.println("error-getAllEmployeeById");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<EmployeeDto>> getAllEmployeeByIds(Set<Long> ids) {
        Set<EmployeeDto> dtos = ids.stream().map(id -> new EmployeeDto(id, "NAN", "NAN", "NAN", "NAN")).collect(Collectors.toSet());
        System.out.println("error-getAllEmployeeByIds");
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<SkillDto>> getAllSkillsByIds(Set<Long> ids) {
        Set<SkillDto> dtos = ids.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toSet());
        System.out.println("error-getAllSkillsByIds");
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SkillDto> getSkillById(Long id) {
        SkillDto dto = new SkillDto(id, "NAN");
        System.out.println("error-getSkillById");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DesignationDto> getDesignationById(Long id) {
        DesignationDto dto = new DesignationDto(id, "NAN");
        System.out.println("error-getAllDesignationById");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<SkillDto>> getAllSkillByEmployeeIds(Set<Long> ids) {
        Set<SkillDto> dtos = ids.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toSet());
        System.out.println("error-getAllSkillsByIds");
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}



