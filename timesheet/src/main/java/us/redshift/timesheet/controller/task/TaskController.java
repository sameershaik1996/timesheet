package us.redshift.timesheet.controller.task;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TaskAssembler;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.dto.task.TaskDto;
import us.redshift.timesheet.service.task.ITaskService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskController {

    private final ITaskService taskService;

    private final TaskAssembler taskAssembler;

    private final ObjectMapper objectMapper;


    private final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    public TaskController(ITaskService taskService, TaskAssembler taskAssembler, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.taskAssembler = taskAssembler;
        this.objectMapper = objectMapper;
    }

    @PostMapping("task/save")
    public ResponseEntity<?> saveTask(@Valid @RequestBody TaskDto taskDto) throws ParseException, JsonProcessingException {
        LOGGER.info("Task Insert {} ", objectMapper.writeValueAsString(taskDto));
        Task task = taskAssembler.convertToEntity(taskDto);
        Task taskSaved = taskService.saveTask(task);
        return new ResponseEntity<>(taskAssembler.convertToDto(taskSaved), HttpStatus.CREATED);
    }

    @PutMapping("task/update")
    public ResponseEntity<?> updateTaskStatus(@Valid @RequestBody List<TaskDto> taskDtos, @RequestParam(value = "status") String status) throws ParseException {
        List<Task> taskList = taskAssembler.convertToEntity(taskDtos);
        List<Task> taskSaved = taskService.updateTask(taskList, TaskStatus.get(status.toUpperCase()));

        return new ResponseEntity<>(taskAssembler.convertToDto(taskSaved), HttpStatus.OK);
    }

    @PutMapping("task/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long taskId, @RequestBody TaskDto taskDto) throws ParseException, JsonProcessingException {
        LOGGER.info("Task  {} ", objectMapper.writeValueAsString(taskDto));
        Task currentTask = taskService.getTaskById(taskId);
        taskAssembler.convertToEntity(taskDto, currentTask);
        TaskDto savedTask = taskAssembler.convertToDto(taskService.updateTask(currentTask));
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @GetMapping("task/get/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable(value = "id") Long id) throws ParseException {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(taskAssembler.convertToDto(task), HttpStatus.OK);
    }

    @GetMapping({"task/get"})
    public ResponseEntity<?> getAllTaskByPagination(@RequestParam(value = "status", defaultValue = "ALL", required = false) String status,
                                                    @RequestParam(value = "skills", required = false) Boolean skills,
                                                    @RequestParam(value = "taskId", required = false) Long taskId,
                                                    @RequestParam(value = "projectId", required = false) Long projectId,
                                                    @RequestParam(value = "employeeId", required = false) Long employeeId,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "limits", defaultValue = "0") Integer limits,
                                                    @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy,
                                                    @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {

        if (taskId != null && skills) {
            List<Long> ids = taskService.findAllSkillsByProjectId(taskId);
            return new ResponseEntity<>(taskAssembler.convertToSkillDto(ids), HttpStatus.OK);
        } else if (projectId != null && employeeId == null) {
            Page<Task> taskPage = taskService.getProjectTasksByPagination(projectId, page, limits, orderBy, fields);
            return new ResponseEntity<>(taskAssembler.convertToPagedDto(taskPage), HttpStatus.OK);
        } else if (projectId != null && employeeId != null && !("ALL".equalsIgnoreCase(status))) {
            List<Task> tasks = taskService.findAllByProjectIdAndEmployeeId(projectId, employeeId, TaskStatus.get(status.toUpperCase()));
//            List<Task> tasks = taskService.findAllByProject_IdAndEmployees_EmployeeIdAndEndDateBeforeOrderByIdAsc(projectId, employeeId, new Date(LocalDate.now().toString()));
            return new ResponseEntity<>(taskAssembler.convertToDto(tasks), HttpStatus.OK);
        } else {
            Page<Task> taskPage = taskService.getAllTaskByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(taskAssembler.convertToProjectTaskPagedDto(taskPage), HttpStatus.OK);
        }

    }

    @GetMapping("task/get/statuses")
    public ResponseEntity<?> getAllTaskStatuses() {
        return new ResponseEntity<>(taskService.getAllTaskStatus(), HttpStatus.OK);
    }

}
