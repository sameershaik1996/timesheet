package us.redshift.timesheet.controller;


import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.service.ITaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("task/save")
    public Task saveTask(@Valid @RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @PutMapping("task/update")
    public Task updateTask(@Valid @RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @GetMapping({"task/"})
    public List<Task> getAllTask() {
        return taskService.getAllTask();
    }

    @GetMapping("task/{id}")
    public Task getTask(@PathVariable(value = "id") Long id) {
        return taskService.getTask(id);
    }

}
