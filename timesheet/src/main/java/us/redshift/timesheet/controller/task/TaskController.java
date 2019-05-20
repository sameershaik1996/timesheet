package us.redshift.timesheet.controller.task;


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
import java.util.Set;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskController {

    private final ITaskService taskService;

    private final TaskAssembler taskAssembler;

    public TaskController(ITaskService taskService, TaskAssembler taskAssembler) {
        this.taskService = taskService;
        this.taskAssembler = taskAssembler;
    }

    @PostMapping("task/save")
    public ResponseEntity<?> saveTask(@Valid @RequestBody TaskDto taskDto) throws ParseException {
        Task task = taskAssembler.convertToEntity(taskDto);
        Task taskSaved = taskService.saveTask(task);
        return new ResponseEntity<>(taskAssembler.convertToDto(taskSaved), HttpStatus.CREATED);
    }

    @PutMapping("task/update")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDto taskDto) throws ParseException {
        Task task = taskAssembler.convertToEntity(taskDto);
        Task taskSaved = taskService.updateTask(task);
        return new ResponseEntity<>(taskAssembler.convertToDto(taskSaved), HttpStatus.OK);
    }

    @GetMapping("task/get/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable(value = "id") Long id) throws ParseException {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(taskAssembler.convertToDto(task), HttpStatus.OK);
    }

    @GetMapping({"task/get"})
    public ResponseEntity<?> getAllTaskByPagination(@RequestParam(value = "status", defaultValue = "ALL", required = false) String status, @RequestParam(value = "projectId", defaultValue = "0", required = false) Long projectId, @RequestParam(value = "employeeId", defaultValue = "0", required = false) Long employeeId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {

        if (projectId != 0 && employeeId == 0) {
            Set<Task> tasks = taskService.getProjectTasksByPagination(projectId, page, limits, orderBy, fields);
            return new ResponseEntity<>(taskAssembler.convertToDto1(tasks), HttpStatus.OK);
        } else if (projectId != 0 && employeeId != 0 && !("ALL".equalsIgnoreCase(status))) {
            Set<Task> tasks = taskService.findAllByProjectIdAndEmployeeId(projectId, employeeId, TaskStatus.get(status.toUpperCase()));
            return new ResponseEntity<>(taskAssembler.convertToDto(tasks), HttpStatus.OK);
        } else {
            Set<Task> tasks = taskService.getAllTaskByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(taskAssembler.convertToDto1(tasks), HttpStatus.OK);
        }

    }

    @GetMapping("task/get/statuses")
    public ResponseEntity<?> getAllTaskStatuses() {
        return new ResponseEntity<>(taskService.getAllTaskStatus(), HttpStatus.OK);
    }

}
