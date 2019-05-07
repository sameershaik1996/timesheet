package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;

    private final IProjectService projectService;

    public TaskService(TaskRepository taskRepository, IProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    @Override
    public Task saveTask(Task task) {

        Project project = projectService.getProject(task.getProject().getId());

        if (project.getStartDate().compareTo(task.getStartDate()) > 0) {
            throw new ValidationException(String.format("The Task Start Date should  be greater then or equal the Project Start Date"));
        }

        if (project.getEndDate().compareTo(task.getStartDate()) < 0) {
            throw new ValidationException(String.format("The Task Start Date should not be greater then the Project End Date"));
        }

        if (project.getEndDate().compareTo(task.getEndDate()) < 0) {
            throw new ValidationException(String.format("The Task End Date should not be greater then the Project End Date"));
        }

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        taskRepository.findById(task.getId()).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", task.getId()));
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTask() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().iterator().forEachRemaining(tasks::add);
        return tasks;
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
    }
}
