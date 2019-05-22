package us.redshift.timesheet.service.task;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.project.ProjectRepository;
import us.redshift.timesheet.reposistory.task.TaskRepository;
import us.redshift.timesheet.util.Reusable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        taskValidate(project, task);
        return taskRepository.save(task);
    }

    @Override
    public Task saveTaskByProjectId(Long projectId, Task task) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", projectId));
        taskValidate(project, task);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        if (!taskRepository.existsById(task.getId()))
            throw new ResourceNotFoundException("Task", "Id", task.getId());
        Project project = projectRepository.findById(task.getProject().getId()).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", task.getProject().getId()));
        taskValidate(project, task);
        return taskRepository.save(task);
    }

    @Override
    public void
    updateTaskHours(Long taskId, BigDecimal usedHour) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", taskId));

        BigDecimal oldUsedHour = task.getUsedHour();

        BigDecimal newUsedHour = oldUsedHour.add(usedHour);
        task.setUsedHour(newUsedHour);

        if (task.getBillableHour().compareTo(newUsedHour) < 0) {
            System.out.println("Task Billable hours Exceeds");
        }

        taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
    }

    @Override
    public Set<Task> getAllTaskByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<Task> taskList = taskRepository.findAll(pageable).getContent();
        Set<Task> taskSet = taskList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskSet;
    }

    @Override
    public Set<Task> getProjectTasksByPagination(Long projectId, int page, int limits, String orderBy, String... fields) {
        if (!projectRepository.existsById(projectId))
            throw new ResourceNotFoundException("Project", "Id", projectId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<Task> taskList = taskRepository.findTaskByProject_Id(projectId, pageable).getContent();
        Set<Task> taskSet = taskList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskSet;
    }

    @Override
    public TaskStatus[] getAllTaskStatus() {
        return TaskStatus.values();
    }

    @Override
    public Set<Task> findAllByStatus(TaskStatus status) {
        return taskRepository.findAllByStatusOrderByIdAsc(status);
    }

    @Override
    public Set<Task> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId, TaskStatus status) {
        return taskRepository.findAllByProjectIdAndEmployeeIdAndStatusOrderByIdAsc(projectId, employeeId, status);
    }

    private void taskValidate(Project project, Task task) {

        if (project.getStartDate() != null && project.getEndDate() != null && task.getStartDate() != null && task.getEndDate() != null) {
            if (project.getStartDate().compareTo(task.getStartDate()) > 0) {
                throw new ValidationException("The Task Start Date should  be greater then or equal the Project Start Date");
            }
            if (project.getEndDate().compareTo(task.getStartDate()) < 0 || project.getEndDate().compareTo(task.getEndDate()) < 0) {
                throw new ValidationException("The Task Start Date / End Date should not be greater then the Project End Date");
            }
            if (task.getStartDate().compareTo(task.getEndDate()) > 0) {
                throw new ValidationException("The Task Start Date should not be greater then the Task End Date");
            }
        }
    }

}
