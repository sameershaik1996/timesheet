package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.timesheet.TimesheetCloneDto;

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
