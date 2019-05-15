package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskCardAssembler {

    private final ModelMapper mapper;

    private final EmployeeFeign employeeFeign;

    public TaskCardAssembler(ModelMapper mapper, EmployeeFeign employeeFeign) {
        this.mapper = mapper;
        this.employeeFeign = employeeFeign;

        //      Long to EmployeeListDto
        Converter<Long, EmployeeListDto> LongToEmployee = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                EmployeeDto employee = this.employeeFeign.getEmployeeById(source).getBody();
                EmployeeListDto dest = mapper.map(employee, EmployeeListDto.class);

                return dest;
            }
            return null;
        };


        //      Long to SkillDto set
        Converter<Long, SkillDto> LongToSkill = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                //          Feign Client Call to get SkillDto
                SkillDto dest = this.employeeFeign.getSkillById(source).getBody();

                return dest;
            }
            return null;

        };


        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TaskCard, TaskCardDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);
                using(LongToSkill).map(source.getSkillId()).setSkill(null);

            }
        });
    }


    public TaskCard convertToEntity(TaskCardDto taskCardDto) throws ParseException {
        TaskCard taskCard = mapper.map(taskCardDto, TaskCard.class);
        return taskCard;
    }

    public TaskCardDto convertToDto(TaskCard taskCard) throws ParseException {
        TaskCardDto taskCardDto = mapper.map(taskCard, TaskCardDto.class);
        return taskCardDto;
    }

    public Set<TaskCard> convertToEntity(Set<TaskCardDto> taskCardDtoSet) throws ParseException {
        Set<TaskCard> taskCardSet = taskCardDtoSet.stream().map(taskCardDto -> {
            return mapper.map(taskCardDto, TaskCard.class);
        }).collect(Collectors.toCollection(HashSet::new));
        return taskCardSet;
    }

    public Set<TaskCardDto> convertToDto(Set<TaskCard> taskCardSet) throws ParseException {
        Set<TaskCardDto> taskCardDtoSet = taskCardSet.stream().map(taskCard -> {
            return mapper.map(taskCard, TaskCardDto.class);
        }).collect(Collectors.toCollection(HashSet::new));
        return taskCardDtoSet;
    }


}
