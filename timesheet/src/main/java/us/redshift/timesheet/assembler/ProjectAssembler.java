package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.dto.BaseDto;
import us.redshift.timesheet.dto.ProjectDto;
import us.redshift.timesheet.dto.ProjectListDto;
import us.redshift.timesheet.feign.EmployeeFeign;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectAssembler {


    private final ModelMapper mapper;
    private final EmployeeFeign employeeFeign;

    public ProjectAssembler(ModelMapper mapper, EmployeeFeign employeeFeign) {
        this.mapper = mapper;
        this.employeeFeign = employeeFeign;
//      BaseDto to Long set
        Converter<Set<BaseDto>, Set<Long>> BaseToLongSet = mappingContext -> {

            Set<BaseDto> source = new HashSet<>();
            if (mappingContext.getSource() != null)
                source = mappingContext.getSource();

//            Set<Long> dest = new HashSet<>();
//            source.forEach(baseDto -> {
//                dest.add(baseDto.getId());
//            });

            Set<Long> dest = source.stream().map(baseDto -> baseDto.getId()).collect(Collectors.toSet());
            return dest;
        };

//      adding BaseDto to long set conversion property
        mapper.addMappings(new PropertyMap<ProjectDto, Project>() {
            protected void configure() {
                using(BaseToLongSet).map(source.getEmployees()).setEmployeeId(null);
            }
        });

//      Long to BaseDto Set
        Converter<Set<Long>, Set<BaseDto>> LongToBaseSet = mappingContext -> {
            Set<Long> source = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
            }
            Set<BaseDto> dest = source.stream().map(id -> new BaseDto(id, "")).collect(Collectors.toSet());
//            Set<BaseDto> dest = employeeFeign.getAllEmployee().getBody();
            return dest;
        };

//      adding long to BaseDto set conversion property
        mapper.addMappings(new PropertyMap<Project, ProjectDto>() {
            protected void configure() {
                using(LongToBaseSet).map(source.getEmployeeId()).setEmployees(null);
            }
        });
//      Long to BaseDto
        Converter<Long, BaseDto> LongToBase = mappingContext -> {
            Long source = new Long(0);
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
            }

            return new BaseDto(source, "");
        };

//      adding long to BaseDto  conversion property
        mapper.addMappings(new PropertyMap<Project, ProjectDto>() {
            protected void configure() {
                using(LongToBase).map(source.getManagerId()).setManager(null);
            }
        });
    }


    public Project convertToEntity(ProjectDto projectDto) throws ParseException {
        return mapper.map(projectDto, Project.class);
    }

    public ProjectDto convertToDto(Project project) throws ParseException {
        return mapper.map(project, ProjectDto.class);
    }

    public List<ProjectListDto> convertToDto(List<Project> projects) throws ParseException {
        Type targetListType = new TypeToken<List<ProjectListDto>>() {
        }.getType();
        List<ProjectListDto> list = mapper.map(projects, targetListType);
        return list;
    }

}
