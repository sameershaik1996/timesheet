package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.ProjectStatus;

import java.util.List;


public interface IProjectService {

    Project saveProject(Project project);

    Project saveProjectByClientId(Long clientId, Project project);

    Project updateProject(Project project);

    Project getProjectId(Long id);

    List<Project> getAllProjectByPagination(int page, int limits, String orderBy, String... fields);

    List<Project> getClientProjectsByPagination(Long clientId, int page, int limits, String orderBy, String... fields);

    List<Project> findAllByEmployeeId(Long employeeId);

    ProjectStatus[] getAllProjectStatus();

}
