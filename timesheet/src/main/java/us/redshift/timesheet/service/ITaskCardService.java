package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.TaskCard;

import java.util.List;

public interface ITaskCardService {

    List<TaskCard> saveTaskCard(List<TaskCard> taskCards);

    List<TaskCard> updateTaskCard(List<TaskCard> taskCards);

    TaskCard getTaskCard(Long id);

    List<TaskCard> getAllTaskCardByPagination(int page, int limits, String orderBy, String... fields);

    void deleteTaskCardById(Long id);

}
