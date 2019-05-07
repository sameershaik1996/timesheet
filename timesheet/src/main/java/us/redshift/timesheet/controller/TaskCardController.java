package us.redshift.timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.domain.TaskCard;
import us.redshift.timesheet.domain.TaskCardDetail;
import us.redshift.timesheet.domain.TimeSheetStatus;
import us.redshift.timesheet.reposistory.TaskCardRepository;
import us.redshift.timesheet.service.ITaskCardService;


import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class TaskCardController {


    private  TaskCardRepository taskCardRepository;
    private final ITaskCardService taskCardService;

    public TaskCardController(ITaskCardService taskCardService,TaskCardRepository taskCardRepository) {
        this.taskCardService = taskCardService;
        this.taskCardRepository=taskCardRepository;
    }

    @PostMapping("taskcard/save")
    public TaskCard saveTaskCard(@Valid @RequestBody TaskCard taskCard) {
        return taskCardService.saveTaskCard(taskCard);
    }

    @PutMapping("taskcard/update")
    public TaskCard updateTaskCard(@Valid @RequestBody TaskCard taskCard) {
        return taskCardService.updateTaskCard(taskCard);
    }

    @GetMapping({"taskcard/"})
    public List<TaskCard> getAllTaskCard() {
        return taskCardService.getAllTaskCard();
    }

    @GetMapping("taskcard/{id}")
    public TaskCard getTaskCard(@PathVariable(value = "id") Long id) {
        return taskCardService.getTaskCard(id);
    }


    @PostMapping("timesheet/copy")
    public ResponseEntity<?> copyTimeSheet(){
        LocalDate dateFrom = getLastWeekStartDate(LocalDate.now());
        LocalDate dateTo = dateFrom.plusDays(6);
        return new ResponseEntity<>(taskCardService.copyTaskCards(asDate(dateFrom).toInstant(),asDate(dateTo).toInstant()), HttpStatus.OK);
       //return new ResponseEntity<>(taskCardRepository.findByDateBetween(asDate(dateFrom).toInstant(),asDate(dateTo).toInstant()), HttpStatus.OK);
}


    private LocalDate getLastWeekStartDate(LocalDate date) {
        int day=date.getDayOfWeek().getValue();
        return date.minusDays(6+day);
    }

    private Date asDate(LocalDate date){
        return java.util.Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
