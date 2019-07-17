package us.redshift.timesheet.feignclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.dto.common.DesignationDto;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.exception.ValidationException;

import java.util.List;

@Component
public class EmployeeFeignClientFallback implements EmployeeFeignClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeFeignClientFallback.class);


    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {

        LOGGER.info("error-getAllEmployeeById");
//        EmployeeDto dto = new EmployeeDto(id, "NAN", "NAN", "NAN");
//        return new ResponseEntity<>(dto, HttpStatus.OK);
        throw new ValidationException("Unable to load TeamMate");
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAllEmployeeByIds(List<Long> ids) {


        LOGGER.info("error-getAllEmployeeByIds");
//        List<EmployeeDto> dtos = ids.stream().map(id -> new EmployeeDto(id, "NAN", "NAN", "NAN")).collect(Collectors.toList());
//        return new ResponseEntity<>(dtos, HttpStatus.OK);
        throw new ValidationException("Unable to load TeamMatesList");
    }


    @Override
    public ResponseEntity<SkillDto> getSkillById(Long id) {


        LOGGER.info("error-getSkillById");
//        SkillDto dto = new SkillDto(id, "NAN");
//        return new ResponseEntity<>(dto, HttpStatus.OK);
        throw new ValidationException("Unable to load Skill");
    }


    @Override
    public ResponseEntity<DesignationDto> getDesignationById(Long id) {


        LOGGER.info("error-getAllDesignationById");
//        DesignationDto dto = new DesignationDto(id, "NAN");
//        return new ResponseEntity<>(dto, HttpStatus.OK);
        throw new ValidationException("Unable to load Designation");
    }

    @Override
    public ResponseEntity<List<SkillDto>> getAllSkillsByIds(List<Long> skillIds) {
        LOGGER.info("error-getAllSkillsByIds");
//        List<SkillDto> dtos = dtos = skillIds.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toList());
//        return new ResponseEntity<>(dtos, HttpStatus.OK);
        throw new ValidationException("Unable to load Skills");
    }


    @Override
    public ResponseEntity<List<SkillDto>> getAllSkillsByEmployeeIds(List<Long> employeeIds) {
        LOGGER.info("error-getAllSkillsByEmployeeIds");
//        List<SkillDto> dtos = employeeIds.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toList());
//        return new ResponseEntity<>(dtos, HttpStatus.OK);
        throw new ValidationException("Unable to load Skills");
    }

    @Override
    public ResponseEntity<List<Long>> getEmployeeBySearch(String search) {

            LOGGER.info("error-getEmployeeBySearch");
//        List<SkillDto> dtos = employeeIds.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toList());
//        return new ResponseEntity<>(dtos, HttpStatus.OK);
            throw new ValidationException("Unable to load employees");
    }


}
