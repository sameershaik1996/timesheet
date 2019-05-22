package us.redshift.timesheet.service.taskcard;

import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.List;
import java.util.Set;

public interface ITaskCardService {

    Set<TaskCard> updateTaskCard(Set<TaskCard> taskCards, TimeSheetStatus status);

    TaskCard getTaskCardById(Long id);

    Set<TaskCard> getAllTaskCardByPagination(int page, int limits, String orderBy, String... fields);

    void deleteTaskCardById(Long id);

    Set<TaskCard> getAllTaskCardByMangerId(Long managerId);


    Set<TaskCard> getTaskCardByStatusAndType(TimeSheetStatus status, TaskType type);

    List<TaskCard> getAllTaskCardByProject(Long projectId);
}
