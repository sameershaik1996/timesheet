package us.redshift.timesheet.controller.taskcard;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TaskCardAssembler;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.service.taskcard.ITaskCardService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskCardController {

    private final ITaskCardService taskCardService;

    private final TaskCardAssembler taskCardAssembler;

    public TaskCardController(ITaskCardService taskCardService, TaskCardAssembler taskCardAssembler) {
        this.taskCardService = taskCardService;
        this.taskCardAssembler = taskCardAssembler;
    }


    @PutMapping("taskcard/update")
    public ResponseEntity<?> updateTaskCard(@Valid @RequestBody List<TaskCardDto> taskCardDtoList, @RequestParam(value = "status", defaultValue = "PENDING") String status) throws ParseException {
        List<TaskCard> taskCardList = taskCardAssembler.convertToEntity(taskCardDtoList);
        List<TaskCard> saveTaskCardList = taskCardService.updateTaskCard(taskCardList, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(taskCardAssembler.convertToDto(saveTaskCardList), HttpStatus.OK);
    }

    @GetMapping({"taskcard/get"})
    public ResponseEntity<?> getAllTaskCardByPagination(@RequestParam(value = "managerId", defaultValue = "0") Long managerId, @RequestParam(value = "projectId", defaultValue = "0") Long projectId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "0") int limits, @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        if (!managerId.equals(Long.valueOf(0))) {
            List<TaskCard> taskCardList = taskCardService.getAllTaskCardByMangerId(managerId);
            return new ResponseEntity<>(taskCardAssembler.convertToDto(taskCardList), HttpStatus.OK);
        } else if (!projectId.equals(Long.valueOf(0))) {
            List<TaskCard> taskCards = taskCardService.getAllTaskCardByProject(projectId);
            System.out.println(taskCards.size());
            return new ResponseEntity<>(taskCardAssembler.convertToDto(taskCards), HttpStatus.OK);
        } else {
            Page<TaskCard> taskCardPage = taskCardService.getAllTaskCardByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(taskCardAssembler.convertToPagedDto(taskCardPage), HttpStatus.OK);
        }
    }

    @GetMapping("taskcard/get/{id}")
    public ResponseEntity<?> getTaskCard(@PathVariable(value = "id") Long id) throws ParseException {
        TaskCard taskCard = taskCardService.getTaskCardById(id);
        return new ResponseEntity<>(taskCardAssembler.convertToDto(taskCard), HttpStatus.OK);
    }

    @DeleteMapping("taskcard/delete/{id}")
    public ResponseEntity<Void> deleteTaskCard(@PathVariable Long id) {
        taskCardService.deleteTaskCardById(id);
        return ResponseEntity.noContent().build();
    }


}