package us.redshift.timesheet.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.domain.TaskStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.ProjectRepository;
import us.redshift.timesheet.reposistory.TaskRepository;
import us.redshift.timesheet.util.Reusable;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;


    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;

    }

    @Override
    public Task saveTask(Task task) {
        Project project = projectRepository.findById(task.getProject().getId()).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", task.getProject().getId()));
//        taskValidate(project, task);
        System.out.println(project.getStartDate());
        System.out.println(task.getStartDate());
        return taskRepository.save(task);
    }

    @Override
    public Task saveTaskByProjectId(Long projectId, Task task) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", projectId));
//        taskValidate(project, task);
//        task.setProject(project);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        if (!taskRepository.existsById(task.getId()))
            throw new ResourceNotFoundException("Task", "Id", task.getId());
        Project project = projectRepository.findById(task.getProject().getId()).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", task.getProject().getId()));
//        taskValidate(project, task);
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
    }

    @Override
    public List<Task> getAllTaskByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Task> getProjectTasksByPagination(Long projectId, int page, int limits, String orderBy, String... fields) {
        if (!projectRepository.existsById(projectId))
            throw new ResourceNotFoundException("Project", "Id", projectId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskRepository.findTaskByProject_Id(projectId, pageable).getContent();
    }

    @Override
    public TaskStatus[] getAllTaskStatus() {
        return TaskStatus.values();
    }

    private void taskValidate(Project project, Task task) {
        if (project.getStartDate().compareTo(task.getStartDate()) > 0) {
            throw new ValidationException("The Task Start Date should  be greater then or equal the Project Start Date");
        }

        if (project.getEndDate().compareTo(task.getStartDate()) < 0) {
            throw new ValidationException("The Task Start Date should not be greater then the Project End Date");
        }

        if (project.getEndDate().compareTo(task.getEndDate()) < 0) {
            throw new ValidationException("The Task End Date should not be greater then the Project End Date");
        }
    }

}
