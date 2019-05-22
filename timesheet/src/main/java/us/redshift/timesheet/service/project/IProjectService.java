package us.redshift.timesheet.service.project;

import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;

import java.util.Set;


public interface IProjectService {

    Project saveProject(Project project);

    Project saveProjectByClientId(Long clientId, Project project);


    Set<Project> updateProject(Set<Project> clients, ProjectStatus status);

    Project getProjectById(Long id);

    Set<Project> getAllProjectByPagination(int page, int limits, String orderBy, String... fields);

    Set<Project> getClientProjectsByPagination(Long clientId, int page, int limits, String orderBy, String... fields);

    Set<Project> findAllByEmployeeId(Long employeeId, ProjectStatus status);

    Set<Long> findAllEmployeesByProjectId(Long projectId);

    ProjectStatus[] getAllProjectStatus();

    Set<Project> findAllByStatus(ProjectStatus status);


}
