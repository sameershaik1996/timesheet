package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.ProjectStatus;
import us.redshift.timesheet.dto.SkillDto;

import java.util.List;
import java.util.Set;


public interface IProjectService {

    Project saveProject(Project project);

    Project saveProjectByClientId(Long clientId, Project project);

    Project updateProject(Project project);

    Project getProjectById(Long id);

    List<Project> getAllProjectByPagination(int page, int limits, String orderBy, String... fields);

    List<Project> getClientProjectsByPagination(Long clientId, int page, int limits, String orderBy, String... fields);

    List<Project> findAllByEmployeeId(Long employeeId, ProjectStatus status);

    Set<SkillDto> findAllSkillsByProjectId(Long projectId);

    ProjectStatus[] getAllProjectStatus();

}
