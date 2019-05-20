package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.dto.taskcard.TaskCardListDto;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskCardAssembler {

    private final ModelMapper mapper;

    private final EmployeeFeign employeeFeign;

    public TaskCardAssembler(ModelMapper mapper, EmployeeFeign employeeFeign) {
        this.mapper = mapper;
        this.employeeFeign = employeeFeign;


        Converter<Set<TaskCardDetailDto>, Set<TaskCardDetail>> taskCardDetailConvertor = mappingContext -> {

            Set<TaskCardDetailDto> source;
            Set<TaskCardDetail> dest = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();


                source.forEach(taskCardDetailDto -> {
                    if (taskCardDetailDto != null) {
                        if (taskCardDetailDto.getHours() != null) {
                            TaskCardDetail taskCardDetail = new TaskCardDetail();
                            taskCardDetail.setId(taskCardDetailDto.getId());
                            taskCardDetail.set_index(taskCardDetailDto.get_index());
                            taskCardDetail.setHours(taskCardDetailDto.getHours());
                            taskCardDetail.setDate(taskCardDetailDto.getDate());
                            taskCardDetail.setComment(taskCardDetailDto.getComment());
                            dest.add(taskCardDetail);
                        }
                    }
                });
            }
            return dest;
        };


        //      Long to EmployeeListDto
        Converter<Long, EmployeeListDto> LongToEmployee = mappingContext -> {
            Long source = Long.valueOf(0);
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                //System.out.println("Task card  Assembler employee");

                EmployeeDto employee = this.employeeFeign.getEmployeeById(source).getBody();
                EmployeeListDto dest = mapper.map(employee, EmployeeListDto.class);

                return dest;
            }
            return null;

        };

        //      Long to EmployeeListDto
        Converter<EmployeeListDto, Long> EmployeeToLong = mappingContext -> {
            if (mappingContext.getSource() != null) {
                return mappingContext.getSource().getId();
            }
            return null;
        };


        //      Long to SkillDto set
        Converter<Long, SkillDto> LongToSkill = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();

                //System.out.println("Task card  Assembler skill");
                //          Feign Client Call to get SkillDto
                SkillDto dest = this.employeeFeign.getSkillById(source).getBody();

                return dest;
            }
            return null;

        };

        //      Long to SkillDto
        Converter<SkillDto, Long> SkillToLong = mappingContext -> {
            if (mappingContext.getSource() != null) {
                return mappingContext.getSource().getId();
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

        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TaskCardDto, TaskCard>() {
            protected void configure() {
                using(EmployeeToLong).map(source.getEmployee()).setEmployeeId(null);
                using(SkillToLong).map(source.getSkill()).setSkillId(null);
                using(taskCardDetailConvertor).map(source.getTaskCardDetails()).setTaskCardDetails(null);

            }
        });


        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TaskCard, TaskCardListDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);
                using(LongToSkill).map(source.getSkillId()).setSkill(null);

            }
        });

        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TaskCardListDto, TaskCard>() {
            protected void configure() {
                using(EmployeeToLong).map(source.getEmployee()).setEmployeeId(null);
                using(SkillToLong).map(source.getSkill()).setSkillId(null);

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

    public List<TaskCardDto> convertToDto1(List<TaskCard> taskCards) throws ParseException {
        List<TaskCardDto> taskCardDtos = taskCards.stream().map(taskCard -> mapper.map(taskCard, TaskCardDto.class)).collect(Collectors.toCollection(ArrayList::new));
        return taskCardDtos;
    }


}
