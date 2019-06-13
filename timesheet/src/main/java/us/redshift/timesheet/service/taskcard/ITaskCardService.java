package us.redshift.timesheet.service.taskcard;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.Date;
import java.util.List;

public interface ITaskCardService {

    List<TaskCard> updateTaskCard(List<TaskCard> taskCards, TimeSheetStatus status);

    TaskCard saveTaskCard(TaskCard taskCard);

    TaskCard getTaskCardById(Long id);

    Page<TaskCard> getAllTaskCardByPagination(int page, int limits, String orderBy, String... fields);

    void deleteTaskCardById(Long id);

    List<TaskCard> getAllTaskCardByMangerId(Long managerId);

    List<TaskCard> getTaskCardByStatusAndType(TimeSheetStatus status, TaskType type);

    List<TaskCard> getAllTaskCardByProject(Long projectId);

    Integer setStatusForTaskCard(String status, Long taskCardId);

    Boolean existsById(Long taskCardId);

    void checkDates(TaskCardDetail taskCardDetail, Date date);

    TaskCard calculateAmount(TaskCard taskCard);

    void UpdateActualDates(Long projectId, Long taskId);

    void TimeSheetStatus(TimeSheet timeSheet);
}
