package us.redshift.timesheet.controller.taskcard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TaskCardDetailAssembler;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.taskcard.TaskCardDetailDto;
import us.redshift.timesheet.service.taskcard.ITaskCardDetailService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskCardDetailController {

    private final ITaskCardDetailService taskCardDetailService;


    private final TaskCardDetailAssembler taskCardDetailAssembler;

    private final ObjectMapper objectMapper;


    private final Logger LOGGER = LoggerFactory.getLogger(TaskCardDetailController.class);


    public TaskCardDetailController(ITaskCardDetailService taskCardDetailService, TaskCardDetailAssembler taskCardDetailAssembler, ObjectMapper objectMapper) {
        this.taskCardDetailService = taskCardDetailService;
        this.taskCardDetailAssembler = taskCardDetailAssembler;
        this.objectMapper = objectMapper;
    }


    @PutMapping("taskcarddetail/update")
    public ResponseEntity<?> updateTaskCardDetail(@Valid @RequestBody List<TaskCardDetailDto> taskCardDetailDtos,
                                                  @RequestParam(value = "status", defaultValue = "SUBMITTED") String status) throws ParseException {
        List<TaskCardDetail> taskCardDetails = taskCardDetailAssembler.convertToEntity(taskCardDetailDtos);
        List<TaskCardDetail> dtos = taskCardDetailService.updateTaskCardDetail(taskCardDetails, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(taskCardDetailAssembler.convertToDto(dtos), HttpStatus.OK);
    }

    @GetMapping({"taskcarddetail/get"})
    public ResponseEntity<?> getTaskCardTaskCardDetailsByPagination(@RequestParam(value = "taskCardId", required = false) Long taskCardId,
                                                                    @RequestParam(value = "timeSheetId", required = false) Long timeSheetId,
                                                                    @RequestParam(value = "projectId", required = false) Long projectId,
                                                                    @RequestParam(value = "status", defaultValue = "PENDING", required = false) String status,
                                                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "limits", defaultValue = "0") Integer limits,
                                                                    @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy,
                                                                    @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                                    @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                                                    @RequestParam(value = "projectIds", required = false) List<Long> projectIds,
                                                                    @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        Page<TaskCardDetail> taskCardDetails;
        //System.out.println((fromDate) + " " + toDate + " " + projectId);
        if (fromDate != null && toDate != null && projectIds != null) {
            //System.out.println((fromDate) + " " + toDate + " " + projectId);
            return new ResponseEntity<>(taskCardDetailAssembler.convertToDto(taskCardDetailService.getTaskCardDetailBetweenDateAndByProject(projectIds, fromDate, toDate)), HttpStatus.OK);
        } else if (taskCardId != null) {
            taskCardDetails = taskCardDetailService.getTaskCardTaskCardDetailsByPagination(taskCardId, page, limits, orderBy, fields);
            return new ResponseEntity<>(taskCardDetailAssembler.convertToPagedDto(taskCardDetails), HttpStatus.OK);
        } else if (timeSheetId != null && projectId != null) {
            taskCardDetails = taskCardDetailService.getTaskCardDetailsByTimeSheetId_ProjectId_statusNotLike(timeSheetId, projectId, TimeSheetStatus.get(status.toUpperCase()), page, limits, orderBy, fields);
            return new ResponseEntity<>(taskCardDetailAssembler.convertToPagedDto(taskCardDetails), HttpStatus.OK);
        } else {
            taskCardDetails = taskCardDetailService.getAllTaskCardDetails(page, limits, orderBy, fields);
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


}
