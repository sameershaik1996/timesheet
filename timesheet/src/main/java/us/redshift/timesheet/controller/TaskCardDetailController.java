package us.redshift.timesheet.controller;

import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.TaskCardDetail;
import us.redshift.timesheet.service.ITaskCardDetailService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskCardDetailController {

    private final ITaskCardDetailService taskCardDetailService;

    public TaskCardDetailController(ITaskCardDetailService taskCardDetailService) {
        this.taskCardDetailService = taskCardDetailService;
    }

    @PostMapping("taskcarddetail/save")
    public TaskCardDetail saveTaskCardDetail(@Valid @RequestBody TaskCardDetail taskCardDetail) {
        return taskCardDetailService.saveTaskCardDetail(taskCardDetail);
    }

    @PutMapping("taskcarddetail/update")
    public TaskCardDetail updateTaskCardDetail(@Valid @RequestBody TaskCardDetail taskCardDetail) {
        return taskCardDetailService.updateTaskCardDetail(taskCardDetail);
    }

    @GetMapping({"taskcarddetail/"})
    public List<TaskCardDetail> getAllTaskCardDetail() {
        return taskCardDetailService.getAllTaskCardDetail();
    }

    @GetMapping("taskcarddetail/{id}")
    public TaskCardDetail getTaskCardDetail(@PathVariable(value = "id") Long id) {
        return taskCardDetailService.getTaskCardDetail(id);
    }

}
