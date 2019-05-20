package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.timesheet.TimeSheetBasicListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;

import us.redshift.timesheet.dto.timesheet.TimesheetCloneDto;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Set;

@Component
public class TimeSheetAssembler {
    private final ModelMapper mapper;

    public TimeSheetAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public TimeSheet convertToEntity(TimeSheetDto timeSheetDto) throws ParseException {
        return mapper.map(timeSheetDto, TimeSheet.class);
    }


    public TimeSheetDto convertToDto(TimeSheet timeSheet) throws ParseException {

        return mapper.map(timeSheet, TimeSheetDto.class);
    }


    public Set<TimeSheetDto> convertToDto(Set<TimeSheet> timeSheets) throws ParseException {
        Type targetSetType = new TypeToken<Set<TimeSheetDto>>() {
        }.getType();
        Set<TimeSheetDto> set = mapper.map(timeSheets, targetSetType);
        return set;
    }

    public Set<TimeSheetBasicListDto> convertToDto1(Set<TimeSheet> timeSheets) throws ParseException {
        Type targetSetType = new TypeToken<Set<TimeSheetBasicListDto>>() {
        }.getType();
        Set<TimeSheetBasicListDto> set = mapper.map(timeSheets, targetSetType);
        return set;
    }

}
