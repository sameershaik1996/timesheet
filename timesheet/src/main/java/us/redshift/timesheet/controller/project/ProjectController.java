package us.redshift.timesheet.controller.project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.ProjectAssembler;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.dto.project.ProjectDto;
import us.redshift.timesheet.service.project.IProjectService;
import us.redshift.timesheet.util.Reusable;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Set;


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
        Project project = projectService.getProjectById(id);
        return new ResponseEntity<>(projectAssembler.convertToDto(project), HttpStatus.OK);
    }

    @GetMapping({"projects"})
    public ResponseEntity<?> getAllProjectByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        Set<Project> projectSet = projectService.getAllProjectByPagination(page, limits, orderBy, fields);
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSet), HttpStatus.OK);
    }


    @GetMapping("client/{clientId}/projects")
    public ResponseEntity<?> getClientProjectsByPagination(@PathVariable(value = "clientId") Long clientId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        Set<Project> projectSet = projectService.getClientProjectsByPagination(clientId, page, limits, orderBy, fields);
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSet), HttpStatus.OK);

    }

    @GetMapping("employee/{employeeId}/projects")
    public ResponseEntity<?> getAllByEmployeeId(@PathVariable(value = "employeeId") Long employeeId) throws ParseException {
        Set<Project> projectSet = projectService.findAllByEmployeeId(employeeId, ProjectStatus.ACTIVE);
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSet), HttpStatus.OK);
    }

    @GetMapping("project/{id}/employees")
    public ResponseEntity<?> getProjectEmployeesById(@PathVariable("id") Long id) {
        Set<Long> ids = projectService.findAllEmployeesByProjectId(id);
        return new ResponseEntity<>(projectAssembler.convertToEmployeeDto(ids), HttpStatus.OK);
    }

    @GetMapping("project/{id}/skills")
    public ResponseEntity<?> getProjectSkillsById(@PathVariable("id") Long id) {
        Set<Long> ids = projectService.findAllEmployeesByProjectId(id);
        return new ResponseEntity<>(projectAssembler.convertToSkillDto(ids), HttpStatus.OK);
    }


    @GetMapping("project/statuses")
    public ResponseEntity<?> getAllProjectStatuses() {
        return new ResponseEntity<>(projectService.getAllProjectStatus(), HttpStatus.OK);
    }

    @GetMapping("project/enddate")
    public ResponseEntity<?> getEndDate(@RequestParam("startDate") String startDate, @RequestParam("estimatedDays") Long estimatedDays) {
        return new ResponseEntity<>(Reusable.calcEndDate(LocalDate.parse(startDate), estimatedDays).toString(), HttpStatus.OK);
    }


}
