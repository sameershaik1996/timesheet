package us.redshift.timesheet.controller.taskcard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.assembler.TaskCardAssembler;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;
import us.redshift.timesheet.service.taskcard.ITaskCardService;

import javax.validation.Valid;
import java.text.ParseException;
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

    @PostMapping("taskcard/save")
    public ResponseEntity<?> saveTaskCard(@Valid @RequestBody Set<TaskCardDto> taskCardDtoSet) throws ParseException {
        Set<TaskCard> taskCardSet = taskCardAssembler.convertToEntity(taskCardDtoSet);
        Set<TaskCard> saveTaskCardset = taskCardService.saveTaskCard(taskCardSet);
        return new ResponseEntity<>(taskCardAssembler.convertToDto(saveTaskCardset), HttpStatus.CREATED);
    }

    @PutMapping("taskcard/update")
    public ResponseEntity<?> updateTaskCard(@Valid @RequestBody Set<TaskCardDto> taskCardDtoSet) throws ParseException {
        Set<TaskCard> taskCardSet = taskCardAssembler.convertToEntity(taskCardDtoSet);
        Set<TaskCard> saveTaskCardset = taskCardService.updateTaskCard(taskCardSet);
        return new ResponseEntity<>(taskCardAssembler.convertToDto(saveTaskCardset), HttpStatus.OK);
    }

    @GetMapping({"taskcard"})
    public ResponseEntity<?> getAllTaskCardByPagination(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limits", defaultValue = "1") int limits, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) throws ParseException {
        Set<TaskCard> taskCardSet = taskCardService.getAllTaskCardByPagination(page, limits, orderBy, fields);
        return new ResponseEntity<>(taskCardAssembler.convertToDto1(taskCardSet), HttpStatus.OK);
    }

    @GetMapping("taskcard/{id}")
    public ResponseEntity<?> getTaskCard(@PathVariable(value = "id") Long id) throws ParseException {
        TaskCard taskCard = taskCardService.getTaskCard(id);
        return new ResponseEntity<>(taskCardAssembler.convertToDto(taskCard), HttpStatus.OK);
    }

    @DeleteMapping("taskcard/{id}")
    public ResponseEntity<Void> deleteTaskCard(@PathVariable Long id) {
        taskCardService.deleteTaskCardById(id);
        return ResponseEntity.noContent().build();
    }


}
