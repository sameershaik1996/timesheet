package us.redshift.timesheet.controller.taskcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TaskCardDetailAssembler;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.service.taskcard.ITaskCardDetailService;
import us.redshift.timesheet.util.Reusable;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
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
        List<TaskCardDetail> dtos = taskCardDetailService.updateTaskCardDetail(taskCardDetails, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(taskCardDetailAssembler.convertToDto(dtos), HttpStatus.OK);
    }

    @GetMapping({"taskcarddetail/get"})
    public ResponseEntity<?> getTaskCardTaskCardDetailsByPagination(@RequestParam(value = "taskCardId", required = false, defaultValue = "0") Long taskCardId,
                                                                    @RequestParam(value = "timeSheetId", defaultValue = "0", required = false) Long timeSheetId,
                                                                    @RequestParam(value = "projectId", defaultValue = "0", required = false) Long projectId,
                                                                    @RequestParam(value = "status", defaultValue = "PENDING", required = false) String status,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "limits", defaultValue = "0") int limits,
                                                                    @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy,
                                                                    @RequestParam(value="fromDate",required = false)@DateTimeFormat(pattern="yyyy-MM-dd")Date fromDate,
                                                                    @RequestParam(value="toDate",required = false)@DateTimeFormat(pattern="yyyy-MM-dd")Date toDate,
                                                                    @RequestParam(value = "projectIds", required = false)List<Long> projectIds,
                                                                    @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException
    {
        Page<TaskCardDetail> taskCardDetails;

        if(fromDate!=null&&toDate!=null&&projectIds!=null){
            System.out.println((fromDate) + " " + toDate + " " + projectId);
            return new ResponseEntity<>(taskCardDetailAssembler.convertToDto(taskCardDetailService.getTaskCardDetailBetweenDateAndByProject(projectIds,fromDate,toDate)), HttpStatus.OK);
        }
        else if (taskCardId != 0) {
            taskCardDetails = taskCardDetailService.getTaskCardTaskCardDetailsByPagination(taskCardId, page, limits, orderBy, fields);
            return new ResponseEntity<>(taskCardDetailAssembler.convertToPagedDto(taskCardDetails), HttpStatus.OK);
        } else if (timeSheetId != 0 && projectId != 0) {
            taskCardDetails = taskCardDetailService.getTaskCardDetailsByTimeSheetId_ProjectId_statusNotLike(timeSheetId, projectId, TimeSheetStatus.get(status.toUpperCase()), page, limits, orderBy, fields);
            return new ResponseEntity<>(taskCardDetailAssembler.convertToPagedDto(taskCardDetails), HttpStatus.OK);
        } else {
            Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
            taskCardDetails = taskCardDetailRepository.findAll(pageable);
            return new ResponseEntity<>(taskCardDetailAssembler.convertToPagedDto(taskCardDetails), HttpStatus.OK);
        }


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


//    @GetMapping("taskcarddetail/get")
//    public ResponseEntity<?> getTaskCardDetail(@RequestParam(value = "timeSheetId", defaultValue = "0") Long timeSheetId, @RequestParam(value = "projectId", defaultValue = "0") Long projectId, @RequestParam(value = "status", defaultValue = "PENDING") String status) throws ParseException {
//        List<TaskCardDetail> taskCardDetails = taskCardDetailRepository.findAllByTaskCard_TimeSheet_IdAndTaskCard_Project_IdAndStatusNotLikeOrderByDate(timeSheetId, projectId, TimeSheetStatus.get(status.toUpperCase()));
//        return new ResponseEntity<>(taskCardDetailAssembler.convertToDto(taskCardDetails), HttpStatus.OK);
//
//    }


}
