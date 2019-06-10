package us.redshift.timesheet.assembler;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.dto.timesheet.TimeSheetBasicListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetDto;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

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
        List<TimeSheetBasicListDto> dtos = mapper.map(timeSheetPage.getContent(), targetListType);
        Page<TimeSheetBasicListDto> page = new Page<TimeSheetBasicListDto>() {
            @Override
            public int getTotalPages() {
                return timeSheetPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return timeSheetPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super TimeSheetBasicListDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return timeSheetPage.getNumber();
            }

            @Override
            public int getSize() {
                return timeSheetPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return timeSheetPage.getNumberOfElements();
            }

            @Override
            public List<TimeSheetBasicListDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return timeSheetPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return timeSheetPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return timeSheetPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return timeSheetPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return timeSheetPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return timeSheetPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return timeSheetPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return timeSheetPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<TimeSheetBasicListDto> iterator() {
                return dtos.iterator();
            }
        };

        return page;
    }

    public Page<TimeSheetDto> convertToPagedDto(Page<TimeSheet> timeSheetPage) {
        Type targetListType = new TypeToken<List<TimeSheetDto>>() {
        }.getType();
        List<TimeSheetDto> dtos = mapper.map(timeSheetPage.getContent(), targetListType);

        Page<TimeSheetDto> page = new Page<TimeSheetDto>() {
            @Override
            public int getTotalPages() {
                return timeSheetPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return timeSheetPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super TimeSheetDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return timeSheetPage.getNumber();
            }

            @Override
            public int getSize() {
                return timeSheetPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return timeSheetPage.getNumberOfElements();
            }

            @Override
            public List<TimeSheetDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return timeSheetPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return timeSheetPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return timeSheetPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return timeSheetPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return timeSheetPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return timeSheetPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return timeSheetPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return timeSheetPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<TimeSheetDto> iterator() {
                return dtos.iterator();
            }
        };

        return page;
    }

}
