package us.redshift.timesheet.feignclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.dto.common.DesignationDto;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.SkillDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeFeignClientFallback implements EmployeeFeignClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeFeignClientFallback.class);


    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {

        LOGGER.info("error-getAllEmployeeById");
        EmployeeDto dto = new EmployeeDto(id, "NAN", "NAN", "NAN", "NAN");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAllEmployeeByIds(List<Long> ids) {


        LOGGER.info("error-getAllEmployeeByIds");
        List<EmployeeDto> dtos = ids.stream().map(id -> new EmployeeDto(id, "NAN", "NAN", "NAN", "NAN")).collect(Collectors.toList());
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
    public ResponseEntity<List<SkillDto>> getAllSkillsByIds(List<Long> skillIds, List<Long> empIds) {

        LOGGER.info("error-getAllSkillsByIds");
        List<SkillDto> dtos;
        if (!skillIds.isEmpty())
            dtos = skillIds.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toList());
        else
            dtos = empIds.stream().map(id -> new SkillDto(id, "NAN")).collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
