package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Project;

import java.util.List;


public interface IProjectService {

    Project saveProject(Project project);

    Project saveProject(Long clientId, Project project);

    Project updateProject(Project project);

    List<Project> getAllProject();

    Project getProject(Long id);

    List<Project> getClientProject(Long clientId);

}
