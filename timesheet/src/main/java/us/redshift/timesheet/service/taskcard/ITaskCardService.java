package us.redshift.timesheet.service.taskcard;

import us.redshift.timesheet.domain.taskcard.TaskCard;

import java.util.Set;

public interface ITaskCardService {

    Set<TaskCard> updateTaskCard(Set<TaskCard> taskCards);

    TaskCard getTaskCardById(Long id);

    Set<TaskCard> getAllTaskCardByPagination(int page, int limits, String orderBy, String... fields);

    void deleteTaskCardById(Long id);

    Set<TaskCard> getAllTaskCardByMangerId(Long managerId);

}
