package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.dto.BaseDto;
import us.redshift.timesheet.dto.ProjectTaskListDto;
import us.redshift.timesheet.dto.TaskDto;
import us.redshift.timesheet.dto.TaskListDto;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskAssembler {

    private final ModelMapper mapper;

    public TaskAssembler(ModelMapper mapper) {
        this.mapper = mapper;

//      BaseDto to Long set
        Converter<Set<BaseDto>, Set<Long>> BaseToLongSet = mappingContext -> {

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

//      adding BaseDto to long set conversion property
        mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            protected void configure() {
                using(BaseToLongSet).map(source.getEmployees()).setEmployeeId(null);
                using(BaseToLongSet).map(source.getSkills()).setSkillId(null);
            }
        });

//      Long to BaseDto set
        Converter<Set<Long>, Set<BaseDto>> LongToBaseSet = mappingContext -> {
            Set<Long> source = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
            }
            Set<BaseDto> dest = source.stream().map(id -> new BaseDto(id, "")).collect(Collectors.toSet());
            return dest;
        };

//      adding long to BaseDto set conversion property
        mapper.addMappings(new PropertyMap<Task, TaskDto>() {
            protected void configure() {
                using(LongToBaseSet).map(source.getEmployeeId()).setEmployees(null);
                using(LongToBaseSet).map(source.getSkillId()).setSkills(null);
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
