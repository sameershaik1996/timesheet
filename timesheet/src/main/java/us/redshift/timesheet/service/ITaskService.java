package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.domain.TaskStatus;

import java.util.List;

public interface ITaskService {

    Task saveTask(Task task);

    Task saveTaskByProjectId(Long projectId, Task task);

    Task updateTask(Task task);

    Task getTaskById(Long id);

    List<Task> getAllTaskByPagination(int page, int limits, String orderBy, String... fields);

    List<Task> getProjectTasksByPagination(Long projectId, int page, int limits, String orderBy, String... fields);

    TaskStatus[] getAllTaskStatus();
}
