package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.dto.common.CommonDto;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.project.ProjectTaskListDto;
import us.redshift.timesheet.dto.task.TaskDto;
import us.redshift.timesheet.dto.task.TaskListDto;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.HashSet;
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
        this.mapper.addMappings(new PropertyMap<TaskDto, Task>() {
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
        this.mapper.addMappings(new PropertyMap<Task, TaskDto>() {
            protected void configure() {
                using(LongToEmployeeSet).map(source.getEmployeeId()).setEmployees(null);
                using(LongToSkillSet).map(source.getSkillId()).setSkills(null);
            }
        });
//      adding long to EmployeeListDto set conversion property
        this.mapper.addMappings(new PropertyMap<Task, TaskListDto>() {
            protected void configure() {
                using(LongToSkillSet).map(source.getSkillId()).setSkills(null);
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

//      adding to Common Property
        this.mapper.addMappings(new PropertyMap<Task, ProjectTaskListDto>() {
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

    public Set<TaskListDto> convertToDto(Set<Task> tasks) throws ParseException {
        Type targetSetType = new TypeToken<Set<TaskListDto>>() {
        }.getType();
        Set<TaskListDto> set = mapper.map(tasks, targetSetType);
        return set;
    }

    public Set<ProjectTaskListDto> convertToDto1(Set<Task> tasks) throws ParseException {

        Set<ProjectTaskListDto> set = tasks.stream().map(task -> {
            return mapper.map(task, ProjectTaskListDto.class);
        }).collect(Collectors.toCollection(HashSet::new));

        return set;
    }
}
