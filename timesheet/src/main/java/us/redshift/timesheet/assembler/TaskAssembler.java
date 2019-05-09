package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.dto.*;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskAssembler {

    private final ModelMapper mapper;
    private final EmployeeFeign employeeFeign;

    public TaskAssembler(ModelMapper mapper, EmployeeFeign employeeFeign) {
        this.mapper = mapper;
        this.employeeFeign = employeeFeign;


//      EmployeeListDto to Long set
        Converter<Set<EmployeeListDto>, Set<Long>> EmployeeToLongSet = mappingContext -> {

            Set<EmployeeListDto> source = new HashSet<>();
            if (mappingContext.getSource() != null)
                source = mappingContext.getSource();

            Set<Long> dest = source.stream().map(employeeListDto -> employeeListDto.getId()).collect(Collectors.toSet());
            return dest;
        };

//      SkillDto to Long set
        Converter<Set<SkillDto>, Set<Long>> SkillToLongSet = mappingContext -> {

            Set<SkillDto> source = new HashSet<>();
            if (mappingContext.getSource() != null)
                source = mappingContext.getSource();

            Set<Long> dest = source.stream().map(skillDto -> skillDto.getId()).collect(Collectors.toSet());
            return dest;
        };

//      adding EmployeeListDto to long set conversion property
        mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            protected void configure() {
                using(EmployeeToLongSet).map(source.getEmployees()).setEmployeeId(null);
                using(SkillToLongSet).map(source.getSkills()).setSkillId(null);
            }
        });

//      Long to EmployeeListDto set
        Converter<Set<Long>, Set<EmployeeListDto>> LongToEmployeeSet = mappingContext -> {
            Set<Long> source = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
            }

//          Feign Client Call to get EmployeeDto
            Set<EmployeeDto> employees = this.employeeFeign.getAllEmployeeByIds(source).getBody();
            Type targetType = new TypeToken<Set<EmployeeListDto>>() {
            }.getType();

//          Convert EmployeeDto to EmployeeListDto
            Set<EmployeeListDto> dest = mapper.map(employees, targetType);
            return dest;
        };

//      Long to SkillDto set
        Converter<Set<Long>, Set<SkillDto>> LongToSkillSet = mappingContext -> {
            Set<Long> source = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
            }

//          Feign Client Call to get SkillDto
            Set<SkillDto> dest = this.employeeFeign.getAllSkillsByIds(source).getBody();

            return dest;
        };

//      adding long to EmployeeListDto set conversion property
        mapper.addMappings(new PropertyMap<Task, TaskDto>() {
            protected void configure() {
                using(LongToEmployeeSet).map(source.getEmployeeId()).setEmployees(null);
                using(LongToSkillSet).map(source.getSkillId()).setSkills(null);
            }
        });


//      Project to CommonDto
        Converter<Project, CommonDto> projectToCommon =
                mappingContext -> {
                    Project source = new Project();
                    if (mappingContext.getSource() != null)
                        source = mappingContext.getSource();
                    return new CommonDto(source.getId(), source.getName());
                };

//      Client to CommonDto
        Converter<Client, CommonDto> clientToCommon =
                mappingContext -> {
                    Client source = new Client();
                    if (mappingContext.getSource() != null)
                        source = mappingContext.getSource();
                    return new CommonDto(source.getId(), source.getName());
                };

//      adding to Common Property
        mapper.addMappings(new PropertyMap<Task, ProjectTaskListDto>() {
            protected void configure() {
                using(projectToCommon).map(source.getProject()).setProject(null);
                using(clientToCommon).map(source.getProject().getClient()).setClient(null);
            }
        });


    }


    public Task convertToEntity(TaskDto taskDto) throws ParseException {
        return mapper.map(taskDto, Task.class);
    }

    public TaskDto convertToDto(Task task) throws ParseException {
        return mapper.map(task, TaskDto.class);
    }

    public List<TaskListDto> convertToDto(List<Task> tasks) throws ParseException {
        Type targetListType = new TypeToken<List<TaskListDto>>() {
        }.getType();
        List<TaskListDto> list = mapper.map(tasks, targetListType);
        return list;
    }

    public List<ProjectTaskListDto> convertToDto1(List<Task> tasks) throws ParseException {

        List<ProjectTaskListDto> list = tasks.stream().map(task -> {
            return mapper.map(task, ProjectTaskListDto.class);
        }).collect(Collectors.toList());

        return list;
    }
}
