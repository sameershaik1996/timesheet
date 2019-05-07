package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.TaskCard;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface ITaskCardService {

    TaskCard saveTaskCard(TaskCard taskCard);

    TaskCard updateTaskCard(TaskCard taskCard);

    List<TaskCard> getAllTaskCard();

    TaskCard getTaskCard(Long id);

    List<TaskCard> copyTaskCards(Instant dateFrom, Instant dateTo);
}
