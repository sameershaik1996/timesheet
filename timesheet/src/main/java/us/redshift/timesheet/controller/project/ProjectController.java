package us.redshift.timesheet.controller.project;

import org.springframework.data.domain.Page;
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

    @PutMapping("project/update")
    public ResponseEntity<?> updateProject(@Valid @RequestBody List<ProjectDto> projectDtos, @RequestParam(value = "status", required = false) String status) throws ParseException {
        List<Project> projectList = projectAssembler.convertToEntity(projectDtos);
        List<Project> projectSaved = projectService.updateProject(projectList, ProjectStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSaved), HttpStatus.OK);
    }

    @GetMapping("project/get/{id}")
    public ResponseEntity<?> getProjectId(@PathVariable(value = "id") Long id) throws ParseException {
        Project project = projectService.getProjectById(id);
        return new ResponseEntity<>(projectAssembler.convertToDto(project), HttpStatus.OK);
    }

    @GetMapping({"project/get"})
    public ResponseEntity<?> getAllProjectByPagination(@RequestParam(value = "projectId", defaultValue = "0", required = false) Long projectId, @RequestParam(value = "employees", defaultValue = "false", required = false) Boolean employees, @RequestParam(value = "skills", defaultValue = "false", required = false) Boolean skills, @RequestParam(value = "status", defaultValue = "ALL", required = false) String status, @RequestParam(value = "clientId", defaultValue = "0", required = false) Long clientId, @RequestParam(value = "employeeId", defaultValue = "0", required = false) Long employeeId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "0") int limits, @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        if (projectId != 0) {
            if (skills) {
                List<Long> ids = projectService.findAllEmployeesByProjectId(projectId);
                return new ResponseEntity<>(projectAssembler.convertToSkillDto(ids), HttpStatus.OK);
            } else if (employees) {
                List<Long> ids = projectService.findAllEmployeesByProjectId(projectId);
                return new ResponseEntity<>(projectAssembler.convertToEmployeeDto(ids), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(projectAssembler.convertToDto(projectService.getProjectById(projectId)), HttpStatus.OK);
            }
        } else if (clientId != 0 && employeeId == 0) {
            Page<Project> projectPage = projectService.getClientProjectsByPagination(clientId, page, limits, orderBy, fields);
            return new ResponseEntity<>(projectAssembler.convertToPagedDto(projectPage), HttpStatus.OK);
        } else if (employeeId != 0 && !("ALL".equalsIgnoreCase(status))) {
            List<Project> projectList = projectService.findAllByEmployeeId(employeeId, ProjectStatus.get(status.toUpperCase()));
            return new ResponseEntity<>(projectAssembler.convertToDto(projectList), HttpStatus.OK);
        } else {
            Page<Project> projectPage = projectService.getAllProjectByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(projectAssembler.convertToPagedDto(projectPage), HttpStatus.OK);
        }
    }

    /*@GetMapping("project/{id}/get/employees")
    public ResponseEntity<?> getProjectEmployeesById(@PathVariable("id") Long id) {
        List<Long> ids = projectService.findAllEmployeesByProjectId(id);
        return new ResponseEntity<>(projectAssembler.convertToEmployeeDto(ids), HttpStatus.OK);
    }

    @GetMapping("project/{id}/get/skills")
    public ResponseEntity<?> getProjectSkillsById(@PathVariable("id") Long id) {
        List<Long> ids = projectService.findAllEmployeesByProjectId(id);
        return new ResponseEntity<>(projectAssembler.convertToSkillDto(ids), HttpStatus.OK);
    }*/

    @GetMapping("project/get/statuses")
    public ResponseEntity<?> getAllProjectStatuses() {
        return new ResponseEntity<>(projectService.getAllProjectStatus(), HttpStatus.OK);
    }

    @GetMapping("project/get/enddate")
    public ResponseEntity<?> getEndDate(@RequestParam("startDate") String startDate, @RequestParam("estimatedDays") Long estimatedDays) {
        return new ResponseEntity<>(Reusable.calcEndDate(LocalDate.parse(startDate), estimatedDays).toString(), HttpStatus.OK);
    }


}
