package us.redshift.timesheet.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TaskAssembler;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.dto.TaskDto;
import us.redshift.timesheet.service.ITaskService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

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

    @PostMapping("project/{projectId}/task/save")
    public ResponseEntity<?> saveTaskByProjectId(@PathVariable(value = "projectId") Long projectId, @Valid @RequestBody TaskDto taskDto) throws ParseException {
        Task task = taskAssembler.convertToEntity(taskDto);
        Task taskSaved = taskService.saveTaskByProjectId(projectId, task);
        return new ResponseEntity<>(taskAssembler.convertToDto(taskSaved), HttpStatus.CREATED);
    }


    @PutMapping("task/update")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDto taskDto) throws ParseException {
        Task task = taskAssembler.convertToEntity(taskDto);
        Task taskSaved = taskService.updateTask(task);
        return new ResponseEntity<>(taskAssembler.convertToDto(taskSaved), HttpStatus.OK);
    }

    @GetMapping("task/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable(value = "id") Long id) throws ParseException {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(taskAssembler.convertToDto(task), HttpStatus.OK);
    }

    @GetMapping({"tasks"})
    public ResponseEntity<?> getAllTaskByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        List<Task> tasks = taskService.getAllTaskByPagination(page, limits, orderBy, fields);
        return new ResponseEntity<>(taskAssembler.convertToDto1(tasks), HttpStatus.OK);
    }

    @GetMapping({"project/{projectId}/tasks"})
    public ResponseEntity<?> getProjectTasksByPagination(@PathVariable(value = "projectId") Long projectId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {

        List<Task> tasks = taskService.getProjectTasksByPagination(projectId, page, limits, orderBy, fields);
        return new ResponseEntity<>(taskAssembler.convertToDto(tasks), HttpStatus.OK);
    }

    @GetMapping("task/statuses")
    public ResponseEntity<?> getAllTaskStatuses() {
        return new ResponseEntity<>(taskService.getAllTaskStatus(), HttpStatus.OK);
    }

}
