package us.redshift.timesheet.controller.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.ProjectAssembler;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.dto.project.ProjectDto;
import us.redshift.timesheet.feignclient.EmployeeFeignClient;
import us.redshift.timesheet.reposistory.project.ProjectRepository;
import us.redshift.timesheet.service.project.IProjectService;
import us.redshift.timesheet.util.Reusable;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("timesheet/v1/api/")
public class ProjectController {


    private final ProjectRepository projectRepository;

    private final IProjectService projectService;

    private final ProjectAssembler projectAssembler;
    private final ObjectMapper objectMapper;

    private final EmployeeFeignClient employeeFeignClient;

    private final Reusable reusable;
    private final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    public ProjectController(ProjectRepository projectRepository,
                             IProjectService projectService,
                             ProjectAssembler projectAssembler,
                             ObjectMapper objectMapper,
                             EmployeeFeignClient employeeFeignClient,
                             Reusable reusable) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.reusable = reusable;
        this.projectAssembler = projectAssembler;
        this.objectMapper = objectMapper;
        this.employeeFeignClient = employeeFeignClient;
    }


    @PostMapping("project/save")
    public ResponseEntity<?> saveProject(@Valid @RequestBody ProjectDto projectDto) throws ParseException, JsonProcessingException {
        LOGGER.info("Project Insert {} ", objectMapper.writeValueAsString(projectDto));
        Project project = projectAssembler.convertToEntity(projectDto);
        Project projectSaved = projectService.saveProject(project);
        return new ResponseEntity<>(projectAssembler.convertToDto(projectSaved), HttpStatus.CREATED);
    }

    @PutMapping("project/update")
    public ResponseEntity<?> updateProjectStatus(@Valid @RequestBody List<ProjectDto> projectDtos, @RequestParam(value = "status", required = false) String status) throws ParseException {
        List<Project> projectList = projectAssembler.convertToEntity(projectDtos);
        List<Project> projectSaved = projectService.updateProject(projectList, ProjectStatus.get(status.toUpperCase()));

        return new ResponseEntity<>(projectAssembler.convertToDto(projectSaved), HttpStatus.OK);
    }

    @PutMapping("project/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable("id") Long projectId, @RequestBody ProjectDto projectDto, @RequestParam(value = "status", required = false) String status) throws ParseException, JsonProcessingException {
        LOGGER.info("Project Update input {} ", objectMapper.writeValueAsString(projectDto));
        Project currentProject = projectService.getProjectById(projectId);
        projectAssembler.convertToEntity(projectDto, currentProject);
        ProjectDto savedProject = projectAssembler.convertToDto(projectService.updateProject(currentProject));
        return new ResponseEntity<>(savedProject, HttpStatus.OK);
    }

    @GetMapping("project/get/{id}")
    public ResponseEntity<?> getProjectId(@PathVariable(value = "id") Long id) throws ParseException, JsonProcessingException {
        Project project = projectService.getProjectById(id);
        return new ResponseEntity<>(projectAssembler.convertToDto(project), HttpStatus.OK);
    }

    @GetMapping({"project/get"})
    public ResponseEntity<?> getAllProjectByPagination(@RequestParam(value = "projectId", required = false) Long projectId,
                                                       @RequestParam(value = "employees", defaultValue = "false", required = false) Boolean employees,
                                                       @RequestParam(value = "skills", defaultValue = "false", required = false) Boolean skills,
                                                       @RequestParam(value = "status", defaultValue = "ALL", required = false) String status,
                                                       @RequestParam(value = "clientId", required = false) Long clientId,
                                                       @RequestParam(value = "employeeId", required = false) Long employeeId,
                                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "limits", defaultValue = "0") Integer limits,
                                                       @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy,
                                                       @RequestParam(value = "search",required = false)String search,
                                                       @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        if(search!=null){
            Page<Project> projects=projectService.searchProjects(search, page, limits, orderBy, fields);
            return new ResponseEntity<>(projectAssembler.convertToPagedDto(projects), HttpStatus.OK);
        }
        else if (projectId != null) {
            if (skills) {
                List<Long> ids = projectService.findAllEmployeesByProjectId(projectId);
                return new ResponseEntity<>(projectAssembler.convertToSkillDto(ids), HttpStatus.OK);
            } else if (employees) {
                List<Long> ids = projectService.findAllEmployeesByProjectId(projectId);
                return new ResponseEntity<>(projectAssembler.convertToEmployeeDto(ids), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(projectAssembler.convertToDto(projectService.getProjectById(projectId)), HttpStatus.OK);
            }
        } else if (clientId != null && employeeId == null) {
            Page<Project> projectPage = projectService.getClientProjectsByPagination(clientId, page, limits, orderBy, fields);
            return new ResponseEntity<>(projectAssembler.convertToPagedDto(projectPage), HttpStatus.OK);
        } else if (employeeId != null && !("ALL".equalsIgnoreCase(status))) {
            //System.out.println("testing...");
            List<Project> projectList = projectService.findAllByEmployeeId(employeeId, ProjectStatus.get(status.toUpperCase()));
            return new ResponseEntity<>(projectAssembler.convertToDto(projectList), HttpStatus.OK);
        } else {
            Page<Project> projectPage = projectService.getAllProjectByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(projectAssembler.convertToPagedDto(projectPage), HttpStatus.OK);
        }
    }

    @GetMapping("project/get/statuses")
    public ResponseEntity<?> getAllProjectStatuses() {
        return new ResponseEntity<>(projectService.getAllProjectStatus(), HttpStatus.OK);
    }

    @GetMapping("project/get/enddate")
    public ResponseEntity<?> getEndDate(@RequestParam("startDate") String startDate, @RequestParam("estimatedDays") Long estimatedDays) {
        //return new ResponseEntity<>(Reusable.calcEndDate(LocalDate.parse(startDate), estimatedDays).toString(), HttpStatus.OK);
      /*  Long epo = Long.parseLong(startDate);

        //System.out.println(epo);

        LocalDate date =
                Instant.ofEpochMilli(epo)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();*/

        LocalDate localDate = reusable.calcEndDate(startDate, estimatedDays);
        //System.out.println(localDate);

        LocalDateTime localDateTime = localDate.atStartOfDay();

        Long l1 = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();

        return new ResponseEntity<>(l1 * 1000, HttpStatus.OK);


    }

    @GetMapping("project/get/test")
    public ResponseEntity<?> getEmployee() {

        //System.out.println( new Date(System.currentTimeMillis()));

        return new ResponseEntity<>(projectRepository.findAllByEmployeeIdAndStartDateLessThanEqualAndStatusOrderByIdAsc(Long.valueOf(7), new Date(System.currentTimeMillis()), ProjectStatus.ACTIVE), HttpStatus.OK);
    }

}
