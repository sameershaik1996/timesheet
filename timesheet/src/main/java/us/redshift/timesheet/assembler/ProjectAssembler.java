package us.redshift.timesheet.assembler;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.project.ProjectDto;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectAssembler {


    private final ModelMapper mapper;

    private final EmployeeFeignClient employeeFeignClient;

    public ProjectAssembler(ModelMapper mapper, EmployeeFeignClient employeeFeignClient) {
        this.mapper = mapper;
        this.employeeFeignClient = employeeFeignClient;


//      EmployeeListDto to Long set
        Converter<List<EmployeeListDto>, List<Long>> EmployeeToLongList = mappingContext -> {
            List<EmployeeListDto> source = new ArrayList<>();
            if (mappingContext.getSource() != null)
                source = mappingContext.getSource();
            List<Long> dest = source.stream().map(employeeListDto -> employeeListDto.getId()).collect(Collectors.toList());
            return dest;
        };

//      adding EmployeeListDto to long set conversion property
        this.mapper.addMappings(new PropertyMap<ProjectDto, Project>() {
            protected void configure() {
                using(EmployeeToLongList).map(source.getEmployees()).setEmployeeId(null);
            }
        });

//      Long to EmployeeListDto List
        Converter<List<Long>, List<EmployeeListDto>> LongToEmployeeList = mappingContext -> {
            List<Long> source = new ArrayList<>();
            if (mappingContext.getSource() != null) {
                source = mappingContext.getSource();
//                //System.out.println(source);

//          Feign Client Call to get EmployeeDto
                List<EmployeeDto> employees = this.employeeFeignClient.getAllEmployeeByIds(source).getBody();


                Type targetType = new TypeToken<List<EmployeeListDto>>() {
                }.getType();

//          Convert EmployeeDto to EmployeeListDto
                List<EmployeeListDto> dest = mapper.map(employees, targetType);
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

                EmployeeDto employee = this.employeeFeignClient.getEmployeeById(source).getBody();
                EmployeeListDto dest = mapper.map(employee, EmployeeListDto.class);

                return dest;
            }
            return null;

        };

//      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<Project, ProjectDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getManagerId()).setManager(null);
                using(LongToEmployeeList).map(source.getEmployeeId()).setEmployees(null);

            }
        });

//      adding long to EmployeeListDto  conversion property
        this.mapper.addMappings(new PropertyMap<Project, ProjectListDto>() {
            protected void configure() {
                using(LongToEmployee).map(source.getManagerId()).setManager(null);
//                using(LongToEmployeeList).map(source.getEmployeeId()).setEmployees(null);
            }
        });


    }


    public Project convertToEntity(ProjectDto projectDto) throws ParseException {
        return mapper.map(projectDto, Project.class);
    }

    public Project convertToEntity(ProjectDto projectDto, Project project) throws ParseException {
        mapper.map(projectDto, project);
        return project;
    }

    public List<Project> convertToEntity(List<ProjectDto> projectDtos) throws ParseException {
        Type targetListType = new TypeToken<List<Project>>() {
        }.getType();
        List<Project> list = mapper.map(projectDtos, targetListType);
        return list;
    }

    public ProjectDto convertToDto(Project project) throws ParseException {
        return mapper.map(project, ProjectDto.class);
    }

    public List<ProjectListDto> convertToDto(List<Project> projects) throws ParseException {

        List<ProjectListDto> List = projects.stream().map(project -> {
            return mapper.map(project, ProjectListDto.class);
        }).collect(Collectors.toCollection(ArrayList::new));

        return List;
    }

    public Page<ProjectListDto> convertToPagedDto(Page<Project> projectPage) {

        List<ProjectListDto> dtos = projectPage.getContent().stream().map(project -> {
            return mapper.map(project, ProjectListDto.class);
        }).collect(Collectors.toCollection(ArrayList::new));

        Page<ProjectListDto> page = new PageImpl<>(dtos,
                new PageRequest(projectPage.getPageable().getPageNumber(), projectPage.getPageable().getPageSize(), projectPage.getPageable().getSort()),
                dtos.size());

        return page;
    }


    public List<EmployeeListDto> convertToEmployeeDto(List<Long> ids) {

        List<EmployeeDto> dtos = employeeFeignClient.getAllEmployeeByIds(ids).getBody();
        Type targetType = new TypeToken<List<EmployeeListDto>>() {
        }.getType();
        return mapper.map(dtos, targetType);
    }

    public List<SkillDto> convertToSkillDto(List<Long> ids) {

        List<SkillDto> dtos = employeeFeignClient.getAllSkillsByIds(null, ids).getBody();
        return dtos;
    }


}
