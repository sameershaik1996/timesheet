package us.redshift.timesheet.controller.taskcard;


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
import java.util.Set;

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
    public ResponseEntity<?> updateTaskCard(@Valid @RequestBody Set<TaskCardDto> taskCardDtoSet, @RequestParam(value = "status", defaultValue = "PENDING") String status) throws ParseException {
        Set<TaskCard> taskCardSet = taskCardAssembler.convertToEntity(taskCardDtoSet);
        Set<TaskCard> saveTaskCardSet = taskCardService.updateTaskCard(taskCardSet, TimeSheetStatus.get(status.toUpperCase()));
        return new ResponseEntity<>(taskCardAssembler.convertToDto(saveTaskCardSet), HttpStatus.OK);
    }

    @GetMapping({"taskcard/get"})
    public ResponseEntity<?> getAllTaskCardByPagination(@RequestParam(value = "managerId", defaultValue = "0") Long managerId, @RequestParam(value = "projectId", defaultValue = "0") Long projectId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "0") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        if (!managerId.equals(Long.valueOf(0))) {
            Set<TaskCard> taskCardSet = taskCardService.getAllTaskCardByMangerId(managerId);
            return new ResponseEntity<>(taskCardAssembler.convertToDto(taskCardSet), HttpStatus.OK);
        } else if (!projectId.equals(Long.valueOf(0))) {
            List<TaskCard> taskCards = taskCardService.getAllTaskCardByProject(projectId);
            System.out.println(taskCards.size());
            return new ResponseEntity<>(taskCardAssembler.convertToDto1(taskCards), HttpStatus.OK);
        } else {
            Set<TaskCard> taskCardSet = taskCardService.getAllTaskCardByPagination(page, limits, orderBy, fields);
            return new ResponseEntity<>(taskCardAssembler.convertToDto(taskCardSet), HttpStatus.OK);
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