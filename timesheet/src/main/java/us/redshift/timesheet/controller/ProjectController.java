package us.redshift.timesheet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.ProjectAssembler;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.dto.ProjectDto;
import us.redshift.timesheet.service.IProjectService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("timesheet/v1/api/")
public class ProjectController {

    private final IProjectService projectService;

    private final ProjectAssembler projectAssembler;


    public ProjectController(IProjectService projectService, ProjectAssembler projectAssembler) {
        this.projectService = projectService;
        this.projectAssembler = projectAssembler;
    }


    @PostMapping("project/save")
    public ResponseEntity<?> saveProject(@Valid @RequestBody ProjectDto projectDto) throws ParseException {
        Project project = projectAssembler.convertToEntity(projectDto);
        Project projectSaved = projectService.saveProject(project);
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSaved), HttpStatus.CREATED);
    }

    @PostMapping("client/{clientId}/project/save")
    public ResponseEntity<?> saveProjectByClientId(@PathVariable(value = "clientId") Long clientId, @Valid @RequestBody ProjectDto projectDto) throws ParseException {
        Project project = projectAssembler.convertToEntity(projectDto);
        Project projectSaved = projectService.saveProjectByClientId(clientId, project);
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSaved), HttpStatus.CREATED);
    }

    @PutMapping("project/update")
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectDto projectDto) throws ParseException {
        Project project = projectAssembler.convertToEntity(projectDto);
        Project projectSaved = projectService.updateProject(project);
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSaved), HttpStatus.OK);
    }

    @GetMapping("project/{id}")
    public ResponseEntity<?> getProjectId(@PathVariable(value = "id") Long id) throws ParseException {
        Project project = projectService.getProjectId(id);
        return new ResponseEntity<>(projectAssembler.convertToDto(project), HttpStatus.OK);
    }

    @GetMapping({"project"})
    public ResponseEntity<?> getAllProjectByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {

        List<Project> projects = projectService.getAllProjectByPagination(page, limits, orderBy, fields);
        return new ResponseEntity<>(projectAssembler.convertToDto(projects), HttpStatus.OK);
    }


    @GetMapping("client/{clientId}/projects")
    public ResponseEntity<?> getClientProjectsByPagination(@PathVariable(value = "clientId") Long clientId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {

        List<Project> projects = projectService.getClientProjectsByPagination(clientId, page, limits, orderBy, fields);
        return new ResponseEntity<>(projectAssembler.convertToDto(projects), HttpStatus.OK);

    }

    @GetMapping("employee/{employeeId}/projects")
    public ResponseEntity<?> getAllByEmployeeId(@PathVariable(value = "employeeId") Long employeeId) throws ParseException {
        List<Project> projects = projectService.findAllByEmployeeId(employeeId);
        return new ResponseEntity<>(projectAssembler.convertToDto(projects), HttpStatus.OK);
    }

    @GetMapping("project/statuses")
    public ResponseEntity<?> getAllProjectStatuses() {
        return new ResponseEntity<>(projectService.getAllProjectStatus(), HttpStatus.OK);
    }

}
