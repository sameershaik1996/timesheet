package us.redshift.timesheet.controller.taskcard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.service.taskcard.ITaskCardDetailService;

import javax.validation.Valid;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskCardDetailController {

    private final ITaskCardDetailService taskCardDetailService;

    public TaskCardDetailController(ITaskCardDetailService taskCardDetailService) {
        this.taskCardDetailService = taskCardDetailService;
    }

    @PostMapping("taskcarddetail/save")
    public ResponseEntity<?> saveTaskCardDetail(@Valid @RequestBody TaskCardDetail taskCardDetail) {
        return new ResponseEntity<>(taskCardDetailService.saveTaskCardDetail(taskCardDetail), HttpStatus.CREATED);
    }

    @PutMapping("taskcarddetail/update")
    public ResponseEntity<?> updateTaskCardDetail(@Valid @RequestBody TaskCardDetail taskCardDetail) {
        return new ResponseEntity<>(taskCardDetailService.updateTaskCardDetail(taskCardDetail), HttpStatus.OK);
    }

    @GetMapping({"taskcard/{taskCardId}/taskcarddetail"})
    public ResponseEntity<?> getTaskCardTaskCardDetailsByPagination(@PathVariable(value = "taskCardId") Long taskCardId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) {
        return new ResponseEntity<>(taskCardDetailService.getTaskCardTaskCardDetailsByPagination(taskCardId, page, limits, orderBy, fields), HttpStatus.OK);
    }

    @GetMapping("taskcarddetail/{id}")
    public ResponseEntity<?> getTaskCardDetail(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(taskCardDetailService.getTaskCardDetail(id), HttpStatus.OK);
    }

    @DeleteMapping("taskcarddetail/{id}")
    public ResponseEntity<Void> deleteTaskCardDetail(@PathVariable Long id) {
        taskCardDetailService.deleteTaskCardDetailById(id);
        return ResponseEntity.noContent().build();
    }

}
