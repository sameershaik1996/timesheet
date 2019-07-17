package us.redshift.timesheet.service.project;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;

import java.util.List;


public interface IProjectService {

    Project saveProject(Project project);

    List<Project> updateProject(List<Project> projects, ProjectStatus status);

    Project updateProject(Project project);


    Project getProjectById(Long id);

    Page<Project> getAllProjectByPagination(int page, int limits, String orderBy, String... fields);

    Page<Project> getClientProjectsByPagination(Long clientId, int page, int limits, String orderBy, String... fields);

    List<Project> findAllByEmployeeId(Long employeeId, ProjectStatus status);

    List<Long> findAllEmployeesByProjectId(Long projectId);

    ProjectStatus[] getAllProjectStatus();

    List<Project> findAllByStatus(ProjectStatus status);

    Boolean existsById(Long projectId);

    List<Project> getAllProjectByManagerId(Long managerId);


    Page<Project> searchProjects(String search, Integer page, Integer limits, String orderBy, String[] fields);

    List<Long> searchProjects(String search);
}
