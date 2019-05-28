package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.dto.common.DesignationDto;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.dto.taskcard.TaskCardListDto;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskCardAssembler {

    private final ModelMapper mapper;

    private final EmployeeFeignClient employeeFeignClient;

    public TaskCardAssembler(ModelMapper mapper, EmployeeFeignClient employeeFeignClient) {
        this.mapper = mapper;
        this.employeeFeignClient = employeeFeignClient;


        Converter<List<TaskCardDetailDto>, List<TaskCardDetail>> taskCardDetailConvertor = mappingContext -> {

            List<TaskCardDetailDto> source;
            List<TaskCardDetail> dest = new ArrayList<>();
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

                EmployeeDto employee = this.employeeFeignClient.getEmployeeById(source).getBody();
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
                SkillDto dest = this.employeeFeignClient.getSkillById(source).getBody();

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


        //      Long to designation
        Converter<Long, DesignationDto> LongToDesignation = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                DesignationDto dest = this.employeeFeignClient.getDesignationById(source).getBody();
                return dest;
            }
            return null;

        };

        //      get designation from employee
        Converter<EmployeeListDto, Long> designationToLong = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource().getId();
                EmployeeDto employee = this.employeeFeignClient.getEmployeeById(source).getBody();
                if (employee.getDesignation() != null)
                    return employee.getDesignation().getId();
                else
                    return Long.valueOf(1);
            }
            return null;
        };


        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TaskCard, TaskCardDto>() {
            protected void configure() {

                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);
                using(LongToSkill).map(source.getSkillId()).setSkill(null);
                using(LongToDesignation).map(source.getDesignationId()).setDesignation(null);
            }
        });


        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TaskCardDto, TaskCard>() {

            protected void configure() {
                using(EmployeeToLong).map(source.getEmployee()).setEmployeeId(null);
                using(SkillToLong).map(source.getSkill()).setSkillId(null);
                using(taskCardDetailConvertor).map(source.getTaskCardDetails()).setTaskCardDetails(null);
                using(designationToLong).map(source.getEmployee()).setDesignationId(null);

            }
        });


        //      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<TaskCard, TaskCardListDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);
                using(LongToSkill).map(source.getSkillId()).setSkill(null);
                using(LongToDesignation).map(source.getDesignationId()).setDesignation(null);

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

    public List<TaskCard> convertToEntity(List<TaskCardDto> taskCardDtoList) throws ParseException {
        List<TaskCard> taskCardList = taskCardDtoList.stream().map(taskCardDto -> {
            return mapper.map(taskCardDto, TaskCard.class);
        }).collect(Collectors.toCollection(ArrayList::new));
        return taskCardList;
    }

    public List<TaskCardDto> convertToDto(List<TaskCard> taskCardList) throws ParseException {
        List<TaskCardDto> taskCardDtoList = taskCardList.stream().map(taskCard -> {
            return mapper.map(taskCard, TaskCardDto.class);
        }).collect(Collectors.toCollection(ArrayList::new));
        return taskCardDtoList;
    }


    public Page<TaskCardDto> convertToPagedDto(Page<TaskCard> taskCardPage) {

        List<TaskCardDto> dtos = taskCardPage.getContent().stream().map(taskCard -> {
            return mapper.map(taskCard, TaskCardDto.class);
        }).collect(Collectors.toCollection(ArrayList::new));

        Page<TaskCardDto> page = new PageImpl<>(dtos,
                new PageRequest(taskCardPage.getPageable().getPageNumber(), taskCardPage.getPageable().getPageSize(), taskCardPage.getPageable().getSort()),
                dtos.size());

        return page;
    }


}
