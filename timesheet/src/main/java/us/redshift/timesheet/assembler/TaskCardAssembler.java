package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;

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
        Page<TaskCardDto> page = new PageImpl<>(dtos,
                new PageRequest(taskCardPage.getPageable().getPageNumber(), taskCardPage.getPageable().getPageSize(), taskCardPage.getPageable().getSort()),
                dtos.size());

        return page;
    }


}
