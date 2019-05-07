package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Task;

import java.util.List;

public interface ITaskService {

    Task saveTask(Task task);

    Task updateTask(Task task);

    List<Task> getAllTask();

    Task getTask(Long id);

}
