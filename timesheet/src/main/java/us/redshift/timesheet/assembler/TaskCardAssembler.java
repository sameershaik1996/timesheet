package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.util.Reusable;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class TaskCardAssembler {

    private final ModelMapper mapper;

    public TaskCardAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public TaskCard convertToEntity(TaskCardDto taskCardDto) {
        return mapper.map(taskCardDto, TaskCard.class);
    }

    public TaskCardDto convertToDto(TaskCard taskCard) {
        return mapper.map(taskCard, TaskCardDto.class);
    }

    public List<TaskCard> convertToEntity(List<TaskCardDto> taskCardDtos) {
        Type targetListType = new TypeToken<List<TaskCard>>() {
        }.getType();
        return mapper.map(taskCardDtos, targetListType);
    }

    public List<TaskCardDto> convertToDto(List<TaskCard> taskCards) {
        Type targetListType = new TypeToken<List<TaskCardDto>>() {
        }.getType();
        return mapper.map(taskCards, targetListType);
    }


    public Page<TaskCardDto> convertToPagedDto(Page<TaskCard> taskCardPage) {
        Type targetListType = new TypeToken<List<TaskCardDto>>() {
        }.getType();
        List<TaskCardDto> dtos = mapper.map(taskCardPage, targetListType);

/*
        Page<TaskCardDto> page = new Page<TaskCardDto>() {
            @Override
            public int getTotalPages() {
                return taskCardPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return taskCardPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super TaskCardDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return taskCardPage.getNumber();
            }

            @Override
            public int getSize() {
                return taskCardPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return taskCardPage.getNumberOfElements();
            }

            @Override
            public List<TaskCardDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return taskCardPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return taskCardPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return taskCardPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return taskCardPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return taskCardPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return taskCardPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return taskCardPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return taskCardPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<TaskCardDto> iterator() {
                return dtos.iterator();
            }
        };*/

        return Reusable.getPaginated(taskCardPage, dtos);
    }


}
