package us.redshift.timesheet.feignclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @GetMapping("/employee/v1/api/skill/get/{id}")
    ResponseEntity<SkillDto> getSkillById(@PathVariable("id") Long id);

    @GetMapping("/employee/v1/api/designation/get/{id}")
    ResponseEntity<DesignationDto> getDesignationById(@PathVariable("id") Long id);

    @GetMapping("/employee/v1/api/skill/get")
    ResponseEntity<Set<SkillDto>> getAllSkillsByIds(@RequestParam("id") Set<Long> skillIds, @RequestParam("empId") Set<Long> empIds);

}

@Component
class EmployeeFeignFallback implements EmployeeFeign {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeFeignFallback.class);


    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {
        LOGGER.info("error-getAllEmployeeById");
        EmployeeDto dto = new EmployeeDto(id, "NAN", "NAN", "NAN", "NAN");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<EmployeeDto>> getAllEmployeeByIds(Set<Long> ids) {


        LOGGER.info("error-getAllEmployeeByIds");
        Set<EmployeeDto> dtos = ids.stream().map(id -> new EmployeeDto(id, "NAN", "NAN", "NAN", "NAN")).collect(Collectors.toSet());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<SkillDto> getSkillById(Long id) {


        LOGGER.info("error-getSkillById");
        SkillDto dto = new SkillDto(id, "NAN");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DesignationDto> getDesignationById(Long id) {


        LOGGER.info("error-getAllDesignationById");
        DesignationDto dto = new DesignationDto(id, "NAN");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<SkillDto>> getAllSkillsByIds(Set<Long> skillIds, Set<Long> empIds) {

        LOGGER.info("error-getAllSkillsByIds");
        Set<SkillDto> dtos;
        if (!skillIds.isEmpty())
            dtos = skillIds.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toSet());
        else
            dtos = empIds.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toSet());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


}



