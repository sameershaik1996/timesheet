package us.redshift.timesheet.assembler;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.dto.project.ProjectTaskListDto;
import us.redshift.timesheet.dto.task.TaskDto;
import us.redshift.timesheet.dto.task.TaskListDto;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Component
public class TaskAssembler {

    private final ModelMapper mapper;


    public TaskAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public Task convertToEntity(TaskDto taskDto) {
        return mapper.map(taskDto, Task.class);
    }

    public Task convertToEntity(TaskDto taskDto, Task task) {
        mapper.map(taskDto, task);
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

        Page<ProjectTaskListDto> page = new Page<ProjectTaskListDto>() {
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
        };

        return page;
    }

    public Page<TaskListDto> convertToPagedDto(Page<Task> taskPage) {

        Type targetListType = new TypeToken<List<TaskListDto>>() {
        }.getType();
        List<TaskListDto> dtos = mapper.map(taskPage.getContent(), targetListType);

        Page<TaskListDto> page = new Page<TaskListDto>() {
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

        return page;
    }

}
