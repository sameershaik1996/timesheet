package us.redshift.timesheet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.TaskCard;
import us.redshift.timesheet.service.ITaskCardService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskCardController {

    private final ITaskCardService taskCardService;

    public TaskCardController(ITaskCardService taskCardService) {
        this.taskCardService = taskCardService;
    }

    @PostMapping("taskcard/save")
    public ResponseEntity<?> saveTaskCard(@Valid @RequestBody List<TaskCard> taskCards) {
        return new ResponseEntity<>(taskCardService.saveTaskCard(taskCards), HttpStatus.CREATED);
    }

    @PutMapping("taskcard/update")
    public ResponseEntity<?> updateTaskCard(@Valid @RequestBody List<TaskCard> taskCards) {
        return new ResponseEntity<>(taskCardService.updateTaskCard(taskCards), HttpStatus.OK);
    }

    @GetMapping({"taskcard"})
    public ResponseEntity<?> getAllTaskCardByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) {
        return new ResponseEntity<>(taskCardService.getAllTaskCardByPagination(page, limits, orderBy, fields), HttpStatus.OK);
    }

    @GetMapping("taskcard/{id}")
    public ResponseEntity<?> getTaskCard(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(taskCardService.getTaskCard(id), HttpStatus.OK);
    }

    @DeleteMapping("taskcard/{id}")
    public ResponseEntity<Void> deleteTaskCard(@PathVariable Long id) {
        taskCardService.deleteTaskCardById(id);
        return ResponseEntity.noContent().build();
    }


}
