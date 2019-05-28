package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import us.redshift.timesheet.feignclient.EmployeeFeignClient;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskAssembler {

    private final ModelMapper mapper;
    private final EmployeeFeignClient employeeFeignClient;

    public TaskAssembler(ModelMapper mapper, EmployeeFeignClient employeeFeignClient) {
        this.mapper = mapper;
        this.employeeFeignClient = employeeFeignClient;


//      EmployeeListDto to Long list
        Converter<List<EmployeeListDto>, List<Long>> EmployeeToLongList = mappingContext -> {

            List<EmployeeListDto> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                //System.out.println("Task Assembler employee long list");
                List<Long> dest = source.stream().map(employeeListDto -> employeeListDto.getId()).collect(Collectors.toList());
                return dest;
            }
            return null;
        };

//      SkillDto to Long list
        Converter<List<SkillDto>, List<Long>> SkillToLongList = mappingContext -> {

            List<SkillDto> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                //System.out.println("Task Assembler skill long list");
                List<Long> dest = source.stream().map(skillDto -> skillDto.getId()).collect(Collectors.toList());
                return dest;
            }
            return null;
        };

//      adding EmployeeListDto to long list conversion property
        this.mapper.addMappings(new PropertyMap<TaskDto, Task>() {
            protected void configure() {
                using(EmployeeToLongList).map(source.getEmployees()).setEmployeeId(null);
                using(SkillToLongList).map(source.getSkills()).setSkillId(null);
            }
        });

//      Long to EmployeeListDto list
        Converter<List<Long>, List<EmployeeListDto>> LongToEmployeeList = mappingContext -> {
            List<Long> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                //          Feign Client Call to get EmployeeDto
                List<EmployeeDto> employees = this.employeeFeignClient.getAllEmployeeByIds(source).getBody();
                Type targetType = new TypeToken<List<EmployeeListDto>>() {
                }.getType();
                //System.out.println("Task Assembler employee list");
//          Convert EmployeeDto to EmployeeListDto
                List<EmployeeListDto> dest = mapper.map(employees, targetType);
                return dest;
            }

            return null;
        };

//      Long to SkillDto list
        Converter<List<Long>, List<SkillDto>> LongToSkillList = mappingContext -> {
            List<Long> source;
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
//          Feign Client Call to get SkillDto
                List<SkillDto> dest = this.employeeFeignClient.getAllSkillsByIds(source, null).getBody();

                return dest;
            }
            return null;
        };

//      adding long to EmployeeListDto list conversion property
        this.mapper.addMappings(new PropertyMap<Task, TaskDto>() {
            protected void configure() {

                using(LongToEmployeeList).map(source.getEmployeeId()).setEmployees(null);
                using(LongToSkillList).map(source.getSkillId()).setSkills(null);
            }
        });
//      adding long to EmployeeListDto list conversion property
        this.mapper.addMappings(new PropertyMap<Task, TaskListDto>() {
            protected void configure() {
                using(LongToSkillList).map(source.getSkillId()).setSkills(null);
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

    public Task convertToEntity(TaskDto taskDto, Task task) throws ParseException {
        mapper.map(taskDto, task);
        return task;
    }

    public List<Task> convertToEntity(List<TaskDto> taskDtos) throws ParseException {
        Type targetListType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> list = mapper.map(taskDtos, targetListType);
        return list;
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
        }).collect(Collectors.toCollection(ArrayList::new));

        return list;
    }

    public Page<ProjectTaskListDto> convertToPagedDto(Page<Task> taskPage) {

        Type targetListType = new TypeToken<List<ProjectTaskListDto>>() {
        }.getType();
        List<ProjectTaskListDto> dtos = mapper.map(taskPage.getContent(), targetListType);

        Page<ProjectTaskListDto> page = new PageImpl<>(dtos,
                new PageRequest(taskPage.getPageable().getPageNumber(), taskPage.getPageable().getPageSize(), taskPage.getPageable().getSort()),
                dtos.size());

        return page;
    }

    public Page<TaskListDto> convertToPagedDto1(Page<Task> taskPage) {

        Type targetListType = new TypeToken<List<TaskListDto>>() {
        }.getType();
        List<TaskListDto> dtos = mapper.map(taskPage.getContent(), targetListType);

        Page<TaskListDto> page = new PageImpl<>(dtos,
                new PageRequest(taskPage.getPageable().getPageNumber(), taskPage.getPageable().getPageSize(), taskPage.getPageable().getSort()),
                dtos.size());

        return page;
    }

}
