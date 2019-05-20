package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.project.ProjectDto;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.feignclient.EmployeeFeign;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectAssembler {


    private final ModelMapper mapper;

    private final EmployeeFeign employeeFeign;

    public ProjectAssembler(ModelMapper mapper, EmployeeFeign employeeFeign) {
        this.mapper = mapper;
        this.employeeFeign = employeeFeign;

//      EmployeeListDto to Long set
        Converter<Set<EmployeeListDto>, Set<Long>> EmployeeToLongSet = mappingContext -> {
            Set<EmployeeListDto> source = new HashSet<>();
            if (mappingContext.getSource() != null)
                source = mappingContext.getSource();
            Set<Long> dest = source.stream().map(employeeListDto -> employeeListDto.getId()).collect(Collectors.toSet());
            return dest;
        };

//      adding EmployeeListDto to long set conversion property
        this.mapper.addMappings(new PropertyMap<ProjectDto, Project>() {
            protected void configure() {
                using(EmployeeToLongSet).map(source.getEmployees()).setEmployeeId(null);
            }
        });

//      Long to EmployeeListDto Set
        Converter<Set<Long>, Set<EmployeeListDto>> LongToEmployeeSet = mappingContext -> {
            Set<Long> source = new HashSet<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
//                //System.out.println(source);

//          Feign Client Call to get EmployeeDto
                Set<EmployeeDto> employees = this.employeeFeign.getAllEmployeeByIds(source).getBody();


                Type targetType = new TypeToken<Set<EmployeeListDto>>() {
                }.getType();

//          Convert EmployeeDto to EmployeeListDto
                Set<EmployeeListDto> dest = mapper.map(employees, targetType);
                return dest;
            }

            return null;
        };


//      Long to EmployeeListDto
        Converter<Long, EmployeeListDto> LongToEmployee = mappingContext -> {
            Long source = new Long(1);
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
                //System.out.println("project Assembler");

                EmployeeDto employee = this.employeeFeign.getEmployeeById(source).getBody();
                EmployeeListDto dest = mapper.map(employee, EmployeeListDto.class);

                return dest;
            }
            return null;

        };

//      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<Project, ProjectDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getManagerId()).setManager(null);
                using(LongToEmployeeSet).map(source.getEmployeeId()).setEmployees(null);

            }
        });

//      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<Project, ProjectListDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getManagerId()).setManager(null);
//                using(LongToEmployeeSet).map(source.getEmployeeId()).setEmployees(null);
            }
        });


    }


    public Project convertToEntity(ProjectDto projectDto) throws ParseException {
        return mapper.map(projectDto, Project.class);
    }

    public Set<Project> convertToEntity(Set<ProjectDto> projectDtos) throws ParseException {
        Type targetListType = new TypeToken<Set<Project>>() {
        }.getType();
        Set<Project> set = mapper.map(projectDtos, targetListType);
        return set;
    }

    public ProjectDto convertToDto(Project project) throws ParseException {
        return mapper.map(project, ProjectDto.class);
    }

    public Set<ProjectListDto> convertToDto(Set<Project> projects) throws ParseException {

        Set<ProjectListDto> set = projects.stream().map(project -> {
            return mapper.map(project, ProjectListDto.class);
        }).collect(Collectors.toCollection(HashSet::new));

        return set;
    }


    public Set<EmployeeListDto> convertToEmployeeDto(Set<Long> ids) {

        Set<EmployeeDto> dtos = employeeFeign.getAllEmployeeByIds(ids).getBody();
        Type targetType = new TypeToken<Set<EmployeeListDto>>() {
        }.getType();
        return mapper.map(dtos, targetType);
    }

    public Set<SkillDto> convertToSkillDto(Set<Long> ids) {

        Set<SkillDto> dtos = employeeFeign.getAllSkillByEmployeeIds(ids).getBody();
        return dtos;
    }


}
