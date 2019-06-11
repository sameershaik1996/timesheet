package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.project.ProjectTaskListDto;
import us.redshift.timesheet.dto.task.TaskDto;
import us.redshift.timesheet.dto.task.TaskListDto;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;
import us.redshift.timesheet.util.Reusable;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class TaskAssembler {

    private final ModelMapper mapper;

    private final EmployeeFeignClient employeeFeignClient;


    public TaskAssembler(ModelMapper mapper, EmployeeFeignClient employeeFeignClient) {
        this.mapper = mapper;
        this.employeeFeignClient = employeeFeignClient;
    }


    public Task convertToEntity(TaskDto taskDto) {
        return mapper.map(taskDto, Task.class);
    }

    public Task convertToEntity(TaskDto taskDto, Task task) {
        Task convertedTask = convertToEntity(taskDto);
        mapper.map(convertedTask, task);
        return task;
    }

    public List<Task> convertToEntity(List<TaskDto> taskDtos) {
        Type targetListType = new TypeToken<List<Task>>() {
        }.getType();
        return mapper.map(taskDtos, targetListType);
    }

    public TaskDto convertToDto(Task task) {
        return mapper.map(task, TaskDto.class);
    }

    public List<TaskListDto> convertToDto(List<Task> tasks) {
        Type targetListType = new TypeToken<List<TaskListDto>>() {
        }.getType();
        return mapper.map(tasks, targetListType);
    }

    public List<ProjectTaskListDto> convertToProjectTaskDto(List<Task> tasks) {
        Type targetListType = new TypeToken<List<ProjectTaskListDto>>() {
        }.getType();
        return mapper.map(tasks, targetListType);
    }

    public Page<ProjectTaskListDto> convertToProjectTaskPagedDto(Page<Task> taskPage) {

        Type targetListType = new TypeToken<List<ProjectTaskListDto>>() {
        }.getType();
        List<ProjectTaskListDto> dtos = mapper.map(taskPage.getContent(), targetListType);

        /*Page<ProjectTaskListDto> page = new Page<ProjectTaskListDto>() {
            @Override
            public int getTotalPages() {
                return taskPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return taskPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super ProjectTaskListDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return taskPage.getNumber();
            }

            @Override
            public int getSize() {
                return taskPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return taskPage.getNumberOfElements();
            }

            @Override
            public List<ProjectTaskListDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return taskPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return taskPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return taskPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return taskPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return taskPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return taskPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return taskPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return taskPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<ProjectTaskListDto> iterator() {
                return dtos.iterator();
            }
        };*/

        return Reusable.getPaginated(taskPage, dtos);

    }

    public Page<TaskListDto> convertToPagedDto(Page<Task> taskPage) {

        Type targetListType = new TypeToken<List<TaskListDto>>() {
        }.getType();
        List<TaskListDto> dtos = mapper.map(taskPage.getContent(), targetListType);

        /*Page<TaskListDto> page = new Page<TaskListDto>() {
            @Override
            public int getTotalPages() {
                return taskPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return taskPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super TaskListDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return taskPage.getNumber();
            }

            @Override
            public int getSize() {
                return taskPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return taskPage.getNumberOfElements();
            }

            @Override
            public List<TaskListDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return taskPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return taskPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return taskPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return taskPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return taskPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return taskPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return taskPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return taskPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<TaskListDto> iterator() {
                return dtos.iterator();
            }
        };
*/
        return Reusable.getPaginated(taskPage, dtos);
    }

    public List<SkillDto> convertToSkillDto(List<Long> employeeIds) {
        List<SkillDto> dtos = employeeFeignClient.getAllSkillsByEmployeeIds(employeeIds).getBody();
        return dtos;
    }

}
