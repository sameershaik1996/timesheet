package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.timesheet.TimeSheetBasicListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class TimeSheetAssembler {
    private final ModelMapper mapper;

    public TimeSheetAssembler(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public TimeSheet convertToEntity(TimeSheetDto timeSheetDto) {
        return mapper.map(timeSheetDto, TimeSheet.class);
    }

    public TimeSheetDto convertToDto(TimeSheet timeSheet) {
        return mapper.map(timeSheet, TimeSheetDto.class);
    }

    public List<TimeSheetDto> convertToDto(List<TimeSheet> timeSheets) {
        Type targetSetType = new TypeToken<List<TimeSheetDto>>() {
        }.getType();
        return mapper.map(timeSheets, targetSetType);
    }

    public Page<TimeSheetBasicListDto> convertToPagedBasicDto(Page<TimeSheet> timeSheetPage) {
        Type targetListType = new TypeToken<List<TimeSheetBasicListDto>>() {
        }.getType();
        List<TimeSheetBasicListDto> TimeSheetBasicListDto = mapper.map(timeSheetPage.getContent(), targetListType);
        Page<TimeSheetBasicListDto> page = new PageImpl<>(TimeSheetBasicListDto,
                new PageRequest(timeSheetPage.getPageable().getPageNumber(), timeSheetPage.getPageable().getPageSize(), timeSheetPage.getPageable().getSort()),
                TimeSheetBasicListDto.size());
        return page;
    }

    public Page<TimeSheetDto> convertToPagedDto(Page<TimeSheet> timeSheetPage) {
        Type targetListType = new TypeToken<List<TimeSheetDto>>() {
        }.getType();
        List<TimeSheetDto> timeSheetDtos = mapper.map(timeSheetPage.getContent(), targetListType);
        Page<TimeSheetDto> page = new PageImpl<>(timeSheetDtos,
                new PageRequest(timeSheetPage.getPageable().getPageNumber(), timeSheetPage.getPageable().getPageSize(), timeSheetPage.getPageable().getSort()),
                timeSheetDtos.size());
        return page;
    }

}
