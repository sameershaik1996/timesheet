package us.redshift.timesheet.assembler;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.modelmapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.common.*;
import us.redshift.timesheet.dto.project.ProjectDto;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.dto.project.ProjectTaskListDto;
import us.redshift.timesheet.dto.task.TaskDto;
import us.redshift.timesheet.dto.task.TaskListDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.dto.taskcard.TaskCardListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetBasicListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetListDto;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;
import us.redshift.timesheet.feignclient.EmployeeFeignClientFallback;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeFeignClientFallback.class);

    private final EmployeeFeignClient employeeFeignClient;

    private final ObjectMapper objectMapper;

    public ModelMapperConfig(EmployeeFeignClient employeeFeignClient, ObjectMapper objectMapper) {
        this.employeeFeignClient = employeeFeignClient;
        this.objectMapper = objectMapper;
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());


//      Long to EmployeeListDto
        Converter<Long, EmployeeListDto> LongToEmployeeListDto = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
//              Feign Client Call to get EmployeeDto
                EmployeeDto employee = new EmployeeDto();
                try {
                    employee = this.employeeFeignClient.getEmployeeById(source).getBody();
                    //LOGGER.info("EmployeeDto {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee));

                } catch (FeignException e) {
                    //System.out.println(e);
                }
                EmployeeListDto dest = mapper.map(employee, EmployeeListDto.class);
                return dest;
            }
            return new EmployeeListDto();

        };

//      EmployeeListDto to Long
        Converter<EmployeeListDto, Long> EmployeeListDtoToLong = mappingContext -> {
            if (mappingContext.getSource() != null) {
                return mappingContext.getSource().getId();
            }
            return Long.valueOf(0);
        };

//      List -> Long to EmployeeListDto
        Converter<List<Long>, List<EmployeeListDto>> listLongToEmployeeListDto = mappingContext -> {
            List<Long> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();

//              Feign Client Call to get EmployeesDto
                List<EmployeeDto> employees = new ArrayList<>();

                try {
                    employees = this.employeeFeignClient.getAllEmployeeByIds(source).getBody();
                    //LOGGER.info(" List<EmployeeDto> {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employees));

                } catch (FeignException e) {
                    //System.out.println(e);
                }
                Type targetType = new TypeToken<List<EmployeeListDto>>() {
                }.getType();

//              Convert EmployeeDto to EmployeeListDto
                List<EmployeeListDto> dest = mapper.map(employees, targetType);

                return dest;
            }

            return new ArrayList<>();
        };

//      List -> EmployeeListDto to Long
        Converter<List<EmployeeListDto>, List<Long>> listEmployeeListDtoToLong = mappingContext -> {
            List<EmployeeListDto> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                List<Long> dest = source.stream().map(employeeListDto -> employeeListDto.getId()).collect(Collectors.toList());
                return dest;
            }
            return new ArrayList<>();
        };


//      Long to SkillDto
        Converter<Long, SkillDto> LongToSkillDto = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                SkillDto dest = new SkillDto();
                try {
                    dest = this.employeeFeignClient.getSkillById(source).getBody();
                    //LOGGER.info(" SkillDto {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dest));

                } catch (FeignException e) {
                    //System.out.println(e);
                }
                return dest;
            }
            return new SkillDto();
        };

//      Long to SkillDto
        Converter<SkillDto, Long> SkillDtoToLong = mappingContext -> {
            if (mappingContext.getSource() != null) {
                return mappingContext.getSource().getId();
            }
            return Long.valueOf(0);
        };

//      List -> Long to SkillDto
        Converter<List<Long>, List<SkillDto>> listLongToSkill = mappingContext -> {
            List<Long> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
//              Feign Client Call to get SkillDto
                List<SkillDto> dest = new ArrayList<>();
                try {
                    dest = this.employeeFeignClient.getAllSkillsByIds(source).getBody();
                    //LOGGER.info("List<SkillDto> {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dest));

                } catch (FeignException e) {
                    //System.out.println(e);
                }
                return dest;
            }
            return new ArrayList<>();
        };

//      list -> SkillDto to Long
        Converter<List<SkillDto>, List<Long>> listSkillToLong = mappingContext -> {
            List<SkillDto> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                List<Long> dest = source.stream().map(skillDto -> skillDto.getId()).collect(Collectors.toList());
                return dest;
            }
            return new ArrayList<>();
        };


//      Long to designationDto
        Converter<Long, DesignationDto> LongToDesignationDto = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
//              Feign Client call to  gat designation
                DesignationDto dest = new DesignationDto();
                try {
                    dest = this.employeeFeignClient.getDesignationById(source).getBody();
                    //LOGGER.info("Designation get {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dest));

                } catch (FeignException e) {
                    //System.out.println(e);
                }

                return dest;
            }
            return new DesignationDto();
        };

//      get designation from employee
        Converter<EmployeeListDto, Long> designationDtoFromEmployeeToLong = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource().getId();
//              Feign Client call to  gat designation
                EmployeeDto employee = new EmployeeDto();
                try {
                    employee = this.employeeFeignClient.getEmployeeById(source).getBody();
                    //LOGGER.info("EmployeeDto get {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee));

                } catch (FeignException e) {
                    //System.out.println(e);
                }
                if (employee.getDesignation() != null)
                    return employee.getDesignation().getId();
                else
                    return Long.valueOf(0);
            }
            return Long.valueOf(0);
        };

        /*------------------------------------------------Add Mapping-------------------------------------------------*/

        /*------------------------------------------------Mapping for Projects-------------------------------------------------*/


//      adding property (Project, ProjectDto)
        mapper.addMappings(new PropertyMap<Project, ProjectDto>() {
            protected void configure() {
                using(LongToEmployeeListDto).map(source.getManagerId()).setManager(null);
                using(listLongToEmployeeListDto).map(source.getEmployeeId()).setEmployees(null);

            }
        });

//      adding property for (Project, ProjectListDto)
        mapper.addMappings(new PropertyMap<Project, ProjectListDto>() {
            protected void configure() {
                using(LongToEmployeeListDto).map(source.getManagerId()).setManager(null);
            }
        });

//      adding property (ProjectDto, Project)
        mapper.addMappings(new PropertyMap<ProjectDto, Project>() {
            protected void configure() {
                using(listEmployeeListDtoToLong).map(source.getEmployees()).setEmployeeId(null);
            }
        });

        /*------------------------------------------------Mapping for Tasks-------------------------------------------------*/

//      adding property (Task, TaskDto)
        mapper.addMappings(new PropertyMap<Task, TaskDto>() {
            protected void configure() {
//                using(listLongToEmployeeListDto).map(source.getEmployeeId()).setEmployees(null);
                using(listLongToSkill).map(source.getSkillId()).setSkills(null);
            }
        });


//      adding property (TaskDto, Task)
        mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            protected void configure() {
//                using(listEmployeeListDtoToLong).map(source.getEmployees()).setEmployeeId(null);
                using(listSkillToLong).map(source.getSkills()).setSkillId(null);
            }
        });


//      Project to CommonDto
        Converter<Project, CommonDto> projectToCommon =
                mappingContext -> {
                    Project source = new Project();
                    if (mappingContext.getSource() != null)
                        source = mappingContext.getSource();
                    return new CommonDto(source.getId(), source.getName(), source.getProjectCode(), source.getStatus().name());
                };

//      Client to CommonDto
        Converter<Client, CommonDto> clientToCommon =
                mappingContext -> {
                    Client source = new Client();
                    if (mappingContext.getSource() != null)
                        source = mappingContext.getSource();
                    return new CommonDto(source.getId(), source.getName(), source.getClientCode(), source.getStatus().name());
                };

//      adding Property (Task, ProjectTaskListDto)
        mapper.addMappings(new PropertyMap<Task, ProjectTaskListDto>() {
            protected void configure() {
                using(projectToCommon).map(source.getProject()).setProject(null);
                using(clientToCommon).map(source.getProject().getClient()).setClient(null);
            }
        });

//      adding long to EmployeeListDto list conversion property
        mapper.addMappings(new PropertyMap<Task, TaskListDto>() {
            protected void configure() {
                using(listLongToSkill).map(source.getSkillId()).setSkills(null);
            }
        });

        /*------------------------------------------------Mapping for TaskCards-------------------------------------------------*/

//      adding property (TaskCard, TaskCardDto)
        mapper.addMappings(new PropertyMap<TaskCard, TaskCardDto>() {
            protected void configure() {

                using(LongToEmployeeListDto).map(source.getEmployeeId()).setEmployee(null);
                using(LongToSkillDto).map(source.getSkillId()).setSkill(null);
                using(LongToDesignationDto).map(source.getDesignationId()).setDesignation(null);
            }
        });

//      adding property for (TaskCard, TaskCardListDto)
        mapper.addMappings(new PropertyMap<TaskCard, TaskCardListDto>() {
            protected void configure() {
                using(LongToEmployeeListDto).map(source.getEmployeeId()).setEmployee(null);
                using(LongToSkillDto).map(source.getSkillId()).setSkill(null);
//                using(LongToDesignationDto).map(source.getDesignationId()).setDesignation(null);

            }
        });


//      TaskCardDetail value 0 Eliminator
        Converter<List<TaskCardDetailDto>, List<TaskCardDetail>> taskCardDetailConverter = mappingContext -> {

            List<TaskCardDetailDto> source;
            List<TaskCardDetail> dest = new ArrayList<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                source.forEach(taskCardDetailDto -> {
                    if (taskCardDetailDto != null) {
                        if (taskCardDetailDto.getHours() != null || BigDecimal.valueOf(0).equals(taskCardDetailDto.getHours())) {
                            TaskCardDetail taskCardDetail = new TaskCardDetail();
                            taskCardDetail.setId(taskCardDetailDto.getId());
                            taskCardDetail.set_index(taskCardDetailDto.get_index());
                            taskCardDetail.setHours(taskCardDetailDto.getHours());
                            taskCardDetail.setDate(taskCardDetailDto.getDate());
                            taskCardDetail.setComment(taskCardDetailDto.getComment());
                            taskCardDetail.setRejectedComment(taskCardDetail.getRejectedComment());
                            dest.add(taskCardDetail);
                        }
                    }
                });
            }
            return dest;
        };


//      adding property for (TaskCardDto, TaskCard)
        mapper.addMappings(new PropertyMap<TaskCardDto, TaskCard>() {
            protected void configure() {
                using(EmployeeListDtoToLong).map(source.getEmployee()).setEmployeeId(null);
                using(SkillDtoToLong).map(source.getSkill()).setSkillId(null);
                using(taskCardDetailConverter).map(source.getTaskCardDetails()).setTaskCardDetails(null);
//                using(designationDtoFromEmployeeToLong).map(source.getEmployee()).setDesignationId(null);
            }
        });

//      adding property for (TaskCardListDto, TaskCard)
        mapper.addMappings(new PropertyMap<TaskCardListDto, TaskCard>() {
            protected void configure() {
                using(EmployeeListDtoToLong).map(source.getEmployee()).setEmployeeId(null);
                using(SkillDtoToLong).map(source.getSkill()).setSkillId(null);


            }
        });



        /*------------------------------------------------Mapping for TimeSheet-------------------------------------------------*/

//      adding property(TimeSheet, TimeSheetDto)
        mapper.addMappings(new PropertyMap<TimeSheet, TimeSheetDto>() {
            protected void configure() {
                using(LongToEmployeeListDto).map(source.getEmployeeId()).setEmployee(null);
            }
        });

//      adding property(TimeSheet, TimeSheetListDto)
        mapper.addMappings(new PropertyMap<TimeSheet, TimeSheetListDto>() {
            protected void configure() {
                using(LongToEmployeeListDto).map(source.getEmployeeId()).setEmployee(null);
            }
        });

//      adding property (TimeSheet, TimeSheetBasicListDto)
        mapper.addMappings(new PropertyMap<TimeSheet, TimeSheetBasicListDto>() {
            protected void configure() {
                using(LongToEmployeeListDto).map(source.getEmployeeId()).setEmployee(null);
            }
        });

        /*------------------------------------------------Mapping for RateCard-------------------------------------------------*/
////      RateCard
//        mapper.addMappings(new PropertyMap<RateCardDetail, RateCardDetailDto>() {
//            protected void configure() {
//                using(LongToDesignationDto).map(source.getDesignationId()).setDesignation(null);
//            }
//        });


        return mapper;
    }


}
