package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;

@Component
public class TaskCardDetailAssembler {

    private final ModelMapper mapper;

    public TaskCardDetailAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<TaskCardDetail> convertToEntity(List<TaskCardDetailDto> taskCardDetailDtos) throws ParseException {

        Type targetListType = new TypeToken<List<TaskCardDetail>>() {
        }.getType();
        List<TaskCardDetail> taskCardDetails = mapper.map(taskCardDetailDtos, targetListType);

        return taskCardDetails;
    }


    public TaskCardDetailDto convertToDto(TaskCardDetail taskCardDetail) throws ParseException {

        return mapper.map(taskCardDetail, TaskCardDetailDto.class);
    }


    public List<TaskCardDetailDto> convertToDto(List<TaskCardDetail> taskCardDetails) throws ParseException {
        Type targetListType = new TypeToken<List<TaskCardDetailDto>>() {
        }.getType();
        List<TaskCardDetailDto> taskCardDetailDtos = mapper.map(taskCardDetails, targetListType);
        return taskCardDetailDtos;
    }
}