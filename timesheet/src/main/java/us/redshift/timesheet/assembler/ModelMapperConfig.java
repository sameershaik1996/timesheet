package us.redshift.timesheet.assembler;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.ratecard.RateCardDetailDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetBasicListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetListDto;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;

@Configuration
public class ModelMapperConfig {

    private final EmployeeFeignClient employeeFeignClient;

    public ModelMapperConfig(EmployeeFeignClient employeeFeignClient) {
        this.employeeFeignClient = employeeFeignClient;
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //      Long to EmployeeListDto
        Converter<Long, EmployeeListDto> LongToEmployee = mappingContext -> {
            Long source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                //System.out.println("TimeSheet Assembler employee");
                EmployeeDto employee = this.employeeFeignClient.getEmployeeById(source).getBody();
                EmployeeListDto dest = mapper.map(employee, EmployeeListDto.class);
                return dest;
            }
            return null;
        };

        //      adding long to EmployeeListDto  conversion property(TimeSheet, TimeSheetDto)
        mapper.addMappings(new PropertyMap<TimeSheet, TimeSheetDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);
            }
        });

        //      adding long to EmployeeListDto  conversion property(TimeSheet, TimeSheetListDto)
        mapper.addMappings(new PropertyMap<TimeSheet, TimeSheetListDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);
            }
        });

        //      adding long to EmployeeListDto  conversion property (TimeSheet, TimeSheetBasicListDto)
        mapper.addMappings(new PropertyMap<TimeSheet, TimeSheetBasicListDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getEmployeeId()).setEmployee(null);
            }
        });
//       RateCard
        mapper.addMappings(new PropertyMap<RateCardDetail, RateCardDetailDto>() {
            protected void configure() {
                map().setLocationId(source.getLocation().getId());
            }
        });

        return mapper;
    }


/*
//      BaseDto to Long
        Converter<Set<BaseDto>, Set<Long>> BaseToLong = mappingContext -> {

            Set<BaseDto> source = new HashSet<>();
            if (mappingContext.getSource() != null)
                source = mappingContext.getSource();

//            Set<Long> dest = new HashSet<>();
//            source.forEach(baseDto -> {
//                dest.add(baseDto.getId());
//            });

            Set<Long> dest = source.stream().map(baseDto -> baseDto.getId()).collect(Collectors.toSet());
            return dest;
        };

//      adding BaseDto to long conversion property
        mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            protected void configure() {
                using(BaseToLong).map(source.getEmployees()).setEmployeeId(null);
                using(BaseToLong).map(source.getSkills()).setSkillId(null);
            }
        });

        mapper.addMappings(new PropertyMap<ProjectDto, Project>() {
            protected void configure() {
                using(BaseToLong).map(source.getEmployees()).setEmployeeId(null);
            }
        });


//      Long to BaseDto
        Converter<Set<Long>, Set<BaseDto>> LongToBase = mappingContext -> {
            Set<Long> source = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
            }
            Set<BaseDto> dest = source.stream().map(id -> new BaseDto(id, "")).collect(Collectors.toSet());
            return dest;
        };

//      adding long to BaseDto conversion property
        mapper.addMappings(new PropertyMap<Task, TaskDto>() {
            protected void configure() {
                using(LongToBase).map(source.getEmployeeId()).setEmployees(null);
                using(LongToBase).map(source.getSkillId()).setSkills(null);
            }
        });


//      Project to BaseDto
        Converter<Project, BaseDto> projectToBase =
                project -> {
                    return new BaseDto(project.getSource().getId(), project.getSource().getName());
                };
//      Client to BaseDto
        Converter<Client, BaseDto> clientToBase =
                client -> {
                    return new BaseDto(client.getSource().getId(), client.getSource().getName());
                };

//      adding toBase Property
        mapper.addMappings(new PropertyMap<Task, ProjectTaskListDto>() {
            protected void configure() {
                using(projectToBase).map(source.getProject()).setProject(null);
                using(clientToBase).map(source.getProject().getClient()).setClient(null);
            }
        });
        return mapper;
    }

*/
}
