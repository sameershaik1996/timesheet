package us.redshift.timesheet.service.task;

import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.task.TaskStatus;

import java.util.Set;

public interface ITaskService {

    Task saveTask(Task task);

    Task saveTaskByProjectId(Long projectId, Task task);

    Task updateTask(Task task);

    Task getTaskById(Long id);

    Set<Task> getAllTaskByPagination(int page, int limits, String orderBy, String... fields);

    Set<Task> getProjectTasksByPagination(Long projectId, int page, int limits, String orderBy, String... fields);

    TaskStatus[] getAllTaskStatus();
}
