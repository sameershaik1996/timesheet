package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.dto.taskcard.TaskCardListDto;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskCardAssembler {

    private final ModelMapper mapper;

    public TaskCardAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public TaskCard convertToEntity(TaskCardDto taskCardDto) throws ParseException {
        TaskCard taskCard = mapper.map(taskCardDto, TaskCard.class);
        return taskCard;
    }

    public TaskCardDto convertToDto(TaskCard taskCard) throws ParseException {
        TaskCardDto taskCardDto = mapper.map(taskCard, TaskCardDto.class);
        return taskCardDto;
    }

    public Set<TaskCard> convertToEntity(Set<TaskCardDto> taskCardDtoSet) throws ParseException {
        Set<TaskCard> taskCardSet = taskCardDtoSet.stream().map(taskCardDto -> {
            return mapper.map(taskCardDto, TaskCard.class);
        }).collect(Collectors.toCollection(HashSet::new));
        return taskCardSet;
    }

    public Set<TaskCardDto> convertToDto(Set<TaskCard> taskCardSet) throws ParseException {
        Set<TaskCardDto> taskCardDtoSet = taskCardSet.stream().map(taskCard -> {
            return mapper.map(taskCard, TaskCardDto.class);
        }).collect(Collectors.toCollection(HashSet::new));
        return taskCardDtoSet;
    }


    public Set<TaskCardListDto> convertToDto1(Set<TaskCard> taskCardSet) throws ParseException {

        Set<TaskCardListDto> taskCardListDtoSet = taskCardSet.stream().map(taskCard -> {
            return mapper.map(taskCard, TaskCardListDto.class);
        }).collect(Collectors.toCollection(HashSet::new));

        return taskCardListDtoSet;
    }
}
