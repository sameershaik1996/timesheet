package us.redshift.timesheet.controller.taskcard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TaskCardDetailAssembler;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.service.taskcard.ITaskCardDetailService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskCardDetailController {

    private final ITaskCardDetailService taskCardDetailService;

    private final TaskCardDetailRepository taskCardDetailRepository;

    private final TaskCardDetailAssembler taskCardDetailAssembler;


    public TaskCardDetailController(ITaskCardDetailService taskCardDetailService, TaskCardDetailRepository taskCardDetailRepository, TaskCardDetailAssembler taskCardDetailAssembler) {
        this.taskCardDetailService = taskCardDetailService;
        this.taskCardDetailRepository = taskCardDetailRepository;
        this.taskCardDetailAssembler = taskCardDetailAssembler;
    }


    @PutMapping("taskcarddetail/update")
    public ResponseEntity<?> updateTaskCardDetail(@Valid @RequestBody List<TaskCardDetailDto> taskCardDetailDtos, @RequestParam(value = "status", defaultValue = "SUBMITTED") String status) throws ParseException {
        List<TaskCardDetail> taskCardDetails = taskCardDetailAssembler.convertToEntity(taskCardDetailDtos);
        System.out.println("stat:" + status + " " + TimeSheetStatus.get(status));
        List<TaskCardDetail> dtos = taskCardDetailService.updateTaskCardDetail(taskCardDetails, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(taskCardDetailAssembler.convertToDto(dtos), HttpStatus.OK);
    }

    @GetMapping({"taskcard/{taskCardId}/taskcarddetail"})
    public ResponseEntity<?> getTaskCardTaskCardDetailsByPagination(@PathVariable(value = "taskCardId") Long taskCardId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) {
        return new ResponseEntity<>(taskCardDetailService.getTaskCardTaskCardDetailsByPagination(taskCardId, page, limits, orderBy, fields), HttpStatus.OK);
    }

    @GetMapping("taskcarddetail/get/{id}")
    public ResponseEntity<?> getTaskCardDetail(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(taskCardDetailService.getTaskCardDetail(id), HttpStatus.OK);
    }

    @DeleteMapping("taskcarddetail/delete/{id}")
    public ResponseEntity<Void> deleteTaskCardDetail(@PathVariable Long id) {
        taskCardDetailService.deleteTaskCardDetailById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("taskcarddetail/get")
    public ResponseEntity<?> getTaskCardDetail(@RequestParam(value = "timeSheetId", defaultValue = "0") Long timeSheetId, @RequestParam(value = "projectId", defaultValue = "0") Long projectId, @RequestParam(value = "status", defaultValue = "PENDING") String status) throws ParseException {
        List<TaskCardDetail> taskCardDetails = taskCardDetailRepository.findAllByTaskCard_TimeSheet_IdAndTaskCard_Project_IdAndStatusNotLikeOrderByDate(timeSheetId, projectId, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(taskCardDetailAssembler.convertToDto(taskCardDetails), HttpStatus.OK);

    }


}
