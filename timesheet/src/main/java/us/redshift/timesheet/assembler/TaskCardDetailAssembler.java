package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Component
public class TaskCardDetailAssembler {

    private final ModelMapper mapper;

    public TaskCardDetailAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<TaskCardDetail> convertToEntity(List<TaskCardDetailDto> taskCardDetailDtos) {
        Type targetListType = new TypeToken<List<TaskCardDetail>>() {
        }.getType();
        return mapper.map(taskCardDetailDtos, targetListType);
    }

    public TaskCardDetailDto convertToDto(TaskCardDetail taskCardDetail) {
        return mapper.map(taskCardDetail, TaskCardDetailDto.class);
    }

    public List<TaskCardDetailDto> convertToDto(List<TaskCardDetail> taskCardDetails) {
        Type targetListType = new TypeToken<List<TaskCardDetailDto>>() {
        }.getType();
        return mapper.map(taskCardDetails, targetListType);
    }

    public Page<TaskCardDetailDto> convertToPagedDto(Page<TaskCardDetail> taskCardDetailsPage) {
        Type targetListType = new TypeToken<List<TaskCardDetailDto>>() {
        }.getType();
        List<TaskCardDetailDto> dtos = mapper.map(taskCardDetailsPage.getContent(), targetListType);

        Page<TaskCardDetailDto> page = new Page<TaskCardDetailDto>() {
            @Override
            public int getTotalPages() {
                return taskCardDetailsPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return taskCardDetailsPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super TaskCardDetailDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return taskCardDetailsPage.getNumber();
            }

            @Override
            public int getSize() {
                return taskCardDetailsPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return taskCardDetailsPage.getNumberOfElements();
            }

            @Override
            public List<TaskCardDetailDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return taskCardDetailsPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return taskCardDetailsPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return taskCardDetailsPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return taskCardDetailsPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return taskCardDetailsPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return taskCardDetailsPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return taskCardDetailsPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return taskCardDetailsPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<TaskCardDetailDto> iterator() {
                return dtos.iterator();
            }
        };

        return page;
    }
}
