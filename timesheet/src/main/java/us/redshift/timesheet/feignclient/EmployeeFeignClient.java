package us.redshift.timesheet.feignclient;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import us.redshift.timesheet.domain.Employee;
import us.redshift.timesheet.dto.common.DesignationDto;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.SkillDto;

import java.util.List;

@FeignClient(url = "${zuul.employee.url}", value = "employeeFeignClient", fallback = EmployeeFeignClientFallback.class)
public interface EmployeeFeignClient {

    @Cacheable(cacheNames = "employees", key = "#id")
    @GetMapping("/employee/v1/api/employee/get/{id}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id);

    @Cacheable(cacheNames = "employees", key = "#ids")
    @GetMapping("/employee/v1/api/employee/get")
    ResponseEntity<List<EmployeeDto>> getAllEmployeeByIds(@RequestParam("id") List<Long> ids);

    @Cacheable(cacheNames = "skills", key = "#id")
    @GetMapping("/employee/v1/api/skill/get/{id}")
    ResponseEntity<SkillDto> getSkillById(@PathVariable("id") Long id);

    @Cacheable(cacheNames = "designations", key = "#id")
    @GetMapping("/employee/v1/api/designation/get/{id}")
    ResponseEntity<DesignationDto> getDesignationById(@PathVariable("id") Long id);

    @Cacheable(cacheNames = "skills", key = "#skillIds")
    @GetMapping("/employee/v1/api/skill/get")
    ResponseEntity<List<SkillDto>> getAllSkillsByIds(@RequestParam("id") List<Long> skillIds);

    @Cacheable(cacheNames = "skills", key = "#employeeIds")
    @GetMapping("/employee/v1/api/skill/get")
    ResponseEntity<List<SkillDto>> getAllSkillsByEmployeeIds(@RequestParam("empId") List<Long> employeeIds);

    @GetMapping("/employee/v1/api/employee/get")
    ResponseEntity<List<Long>> getEmployeeBySearch(@RequestParam("fullSearch") String search);

}




