package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;
import us.redshift.timesheet.dto.timesheet.TimesheetCloneDto;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.text.ParseException;

@Component
public class TimeSheetCloneAssembler {

    private final ModelMapper mapper;

    public TimeSheetCloneAssembler(ModelMapper mapper) {
        this.mapper = mapper;


    }

    public TimesheetCloneDto convertToCloneDto(TimeSheet timeSheet) throws ParseException {

        return mapper.map(timeSheet, TimesheetCloneDto.class);
    }

    public TimeSheet convertCloneToEntity(TimesheetCloneDto timesheetCloneDto) throws ParseException {

        return mapper.map(timesheetCloneDto, TimeSheet.class);
    }


}
