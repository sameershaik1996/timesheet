package us.redshift.timesheet.controller;

import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.service.IProjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class ProjectController {

    private final IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("project/save")
    public Project saveProject(@Valid @RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @PostMapping("client/{clientId}/project/save")
    public Project getClientProject(@PathVariable(value = "clientId") Long clientId, @Valid @RequestBody Project project) {
        return projectService.saveProject(clientId, project);
    }


    @PutMapping("project/update")
    public Project updateProject(@Valid @RequestBody Project project) {
        return projectService.updateProject(project);
    }

    @GetMapping({"project/"})
    public List<Project> getAllProject() {
        return projectService.getAllProject();
    }

    @GetMapping("project/{id}")
    public Project getProject(@PathVariable(value = "id") Long id) {
        return projectService.getProject(id);
    }

    @GetMapping("client/{clientId}/project")
    public List<Project> getClientProject(@PathVariable(value = "clientId") Long clientId) {
        return projectService.getClientProject(clientId);
    }

}
