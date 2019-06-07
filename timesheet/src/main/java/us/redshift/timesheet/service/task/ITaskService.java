package us.redshift.timesheet.service.task;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.task.TaskStatus;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface ITaskService {

    Task saveTask(Task task) throws ParseException;

    List<Task> updateTask(List<Task> tasks, TaskStatus status);

    Task updateTask(Task task) throws ParseException;

    void updateTaskHours(Long taskId, BigDecimal usedHour);

    Task getTaskById(Long id);

    Page<Task> getAllTaskByPagination(int page, int limits, String orderBy, String... fields);

    Page<Task> getProjectTasksByPagination(Long projectId, int page, int limits, String orderBy, String... fields);

    TaskStatus[] getAllTaskStatus();

    List<Task> findAllByStatus(TaskStatus status);

    List<Task> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId, TaskStatus status);
}
