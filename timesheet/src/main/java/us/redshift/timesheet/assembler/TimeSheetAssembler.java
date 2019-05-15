package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Set;

@Component
public class TimeSheetAssembler {
    private final ModelMapper mapper;
    private final EmployeeFeign employeeFeign;

    public TimeSheetAssembler(ModelMapper mapper, EmployeeFeign employeeFeign) {
        this.mapper = mapper;
        this.employeeFeign = employeeFeign;
        //      Long to EmployeeListDto
        Converter<Long, EmployeeListDto> LongToEmployee = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                System.out.println(source);
                EmployeeDto employee = this.employeeFeign.getEmployeeById(source).getBody();
                EmployeeListDto dest = mapper.map(employee, EmployeeListDto.class);

                return dest;
            }
            return null;
        };

        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TimeSheet, TimeSheetDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);


            }
        });

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

}
