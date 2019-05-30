package us.redshift.timesheet.assembler;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.project.ProjectDto;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Component
public class ProjectAssembler {


    private final ModelMapper mapper;

    private final EmployeeFeignClient employeeFeignClient;

    public ProjectAssembler(ModelMapper mapper, EmployeeFeignClient employeeFeignClient) {
        this.mapper = mapper;
        this.employeeFeignClient = employeeFeignClient;
    }


    public Project convertToEntity(ProjectDto projectDto) {
        return mapper.map(projectDto, Project.class);
    }

    public Project convertToEntity(ProjectDto projectDto, Project project) {
        mapper.map(projectDto, project);
        return project;
    }

    public List<Project> convertToEntity(List<ProjectDto> projectDtos) {
        Type targetListType = new TypeToken<List<Project>>() {
        }.getType();
        return mapper.map(projectDtos, targetListType);
    }

    public ProjectDto convertToDto(Project project) {
        return mapper.map(project, ProjectDto.class);
    }

    public List<ProjectListDto> convertToDto(List<Project> projects) {
        Type targetListType = new TypeToken<List<ProjectListDto>>() {
        }.getType();
        return mapper.map(projects, targetListType);
    }

    public Page<ProjectListDto> convertToPagedDto(Page<Project> projectPage) {

        Type targetListType = new TypeToken<List<ProjectListDto>>() {
        }.getType();
        List<ProjectListDto> dtos = mapper.map(projectPage.getContent(), targetListType);

        Page<ProjectListDto> page = new Page<ProjectListDto>() {
            @Override
            public int getTotalPages() {
                return projectPage.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return projectPage.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super ProjectListDto, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return projectPage.getNumber();
            }

            @Override
            public int getSize() {
                return projectPage.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return projectPage.getNumberOfElements();
            }

            @Override
            public List<ProjectListDto> getContent() {
                return dtos;
            }

            @Override
            public boolean hasContent() {
                return projectPage.hasContent();
            }

            @Override
            public Sort getSort() {
                return projectPage.getSort();
            }

            @Override
            public boolean isFirst() {
                return projectPage.isFirst();
            }

            @Override
            public boolean isLast() {
                return projectPage.isLast();
            }

            @Override
            public boolean hasNext() {
                return projectPage.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return projectPage.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return projectPage.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return projectPage.previousPageable();
            }

            @NotNull
            @Override
            public Iterator<ProjectListDto> iterator() {
                return dtos.iterator();
            }
        };

        return page;
    }


    public List<EmployeeListDto> convertToEmployeeDto(List<Long> ids) {
        List<EmployeeDto> dtos = employeeFeignClient.getAllEmployeeByIds(ids).getBody();
        Type targetType = new TypeToken<List<EmployeeListDto>>() {
        }.getType();
        return mapper.map(dtos, targetType);
    }

    public List<SkillDto> convertToSkillDto(List<Long> employeeIds) {
        List<SkillDto> dtos = employeeFeignClient.getAllSkillsByEmployeeIds(employeeIds).getBody();
        return dtos;
    }


}
