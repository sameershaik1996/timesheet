package us.redshift.timesheet.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Employee;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.task.TaskRepository;
import us.redshift.timesheet.service.project.IProjectService;
import us.redshift.timesheet.util.Reusable;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final IProjectService projectService;


    public TaskService(TaskRepository taskRepository,
                       @Lazy IProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;

    }


    @Override
    public Task saveTask(Task task) throws ParseException {
        Project project = projectService.getProjectById(task.getProject().getId());
        taskValidate(project, task);
        task = addEmployee(task);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> updateTask(List<Task> tasks, TaskStatus status) {
        List<Task> taskList = new ArrayList<>();
        tasks.forEach(task -> {
            if (status != null) {
                Task getTask = taskRepository.findById(task.getId()).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", task.getId()));
                if (TaskStatus.COMPLETE.equals(status))
                    getTask = updateActualDate(getTask);
                getTask.setStatus(status);
                taskList.add(getTask);
            }
        });
        return taskRepository.saveAll(taskList);
    }

    @Override
    public Task updateTask(Task task) throws ParseException {
        if (!taskRepository.existsById(task.getId()))
            throw new ResourceNotFoundException("Task", "Id", task.getId());
        Project project = projectService.getProjectById(task.getProject().getId());
        taskValidate(project, task);
        if (TaskStatus.COMPLETE.equals(task.getStatus()))
            task = updateActualDate(task);
        task = addEmployee(task);
        return taskRepository.save(task);
    }

    @Override
    public void updateTaskHours(Long taskId, BigDecimal usedHour) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", taskId));

        BigDecimal oldUsedHour = new BigDecimal(0);
        if (task.getUsedHour() != null)
            oldUsedHour = task.getUsedHour();

        BigDecimal newUsedHour = oldUsedHour.add(usedHour);
        LOGGER.info("OldUsedHour {} and NewUsedHour {} for the task Id {}", oldUsedHour, newUsedHour, taskId);
        task.setUsedHour(newUsedHour);

        if (task.getBillableHour().compareTo(newUsedHour) < 0) {
            LOGGER.warn("Task Billable hours Exceeds");
        }

        taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", id));
    }

    @Override
    public Page<Task> getAllTaskByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
//        List<Task> taskList = taskRepository.findAll(pageable).getContent();
//        Set<Task> taskSet = taskList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> getProjectTasksByPagination(Long projectId, int page, int limits, String orderBy, String... fields) {
        if (!projectService.existsById(projectId))
            throw new ResourceNotFoundException("Project", "Id", projectId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
//        List<Task> taskList = taskRepository.findTaskByProject_Id(projectId, pageable).getContent();
//        Set<Task> taskSet = taskList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskRepository.findTaskByProject_Id(projectId, pageable);
    }

    @Override
    public TaskStatus[] getAllTaskStatus() {
        return TaskStatus.values();
    }

    @Override
    public List<Task> findAllByStatus(TaskStatus status) {
        return taskRepository.findAllByStatusOrderByIdAsc(status);
    }

    @Override
    public List<Task> findAllByProjectIdAndEmployeeId(Long projectId, Long employeeId, TaskStatus status) {

        Set<Task> tasks = new LinkedHashSet<>(taskRepository.findAllByProject_IdAndEmployees_EmployeeIdAndStartDateLessThanEqualAndStatusOrderByIdAsc(projectId, employeeId, new Date(System.currentTimeMillis()), status));
        return tasks.stream().collect(Collectors.toList());
    }

    @Override
    public List<Long> findAllSkillsByProjectId(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", taskId));
        return task.getSkillId();
    }

    @Override
    public Page<Task> getTaskBySearch(String search, Integer page, Integer limits, String orderBy, String[] fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<Long> projectIds=projectService.searchProjects(search);
        if(projectIds.size()==0)
            projectIds=null;
        Page<Task> tasks=taskRepository.searchTasks(search,search,projectIds,pageable);
        return  tasks;
    }


    private void taskValidate(Project project, Task task) throws ParseException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if (project.getStartDate() != null && project.getEndDate() != null && task.getStartDate() != null && task.getEndDate() != null) {

            Date ProjectStartDate = simpleDateFormat.parse(simpleDateFormat.format(project.getStartDate()));
            Date projectEndDate = simpleDateFormat.parse(simpleDateFormat.format(project.getEndDate()));
            Date taskStartDate = simpleDateFormat.parse(simpleDateFormat.format(task.getStartDate()));
            Date taskEndDate = simpleDateFormat.parse(simpleDateFormat.format(task.getEndDate()));


            if (ProjectStartDate.compareTo(taskStartDate) > 0) {
                LOGGER.error("The Task Start Date ( " + taskStartDate + " ) should  be greater then or equal the Project Start Date ( " + project.getStartDate() + " )");
                throw new ValidationException("The Task Start Date ( " + simpleDateFormat.format(task.getStartDate()) + " ) should be same or after the Project Start Date ( " + project.getStartDate() + " )");
            }
            if (projectEndDate.compareTo(taskStartDate) < 0) {
                LOGGER.error("The Task Start Date  ( " + task.getStartDate() + " ) should not be greater then the Project End Date ( " + project.getEndDate() + " )");
                throw new ValidationException("The Task Start Date  ( " + simpleDateFormat.format(task.getStartDate()) + " ) should be before the Project End Date ( " + project.getEndDate() + " )");
            }
            if (project.getEndDate().compareTo(taskEndDate) < 0) {
                LOGGER.error("The Task End Date ( " + task.getEndDate() + " ) should not be greater then the Project End Date ( " + project.getEndDate() + " )");
                throw new ValidationException("The Task End Date ( " + simpleDateFormat.format(task.getEndDate()) + " ) should be same or before the Project End Date ( " + project.getEndDate() + " )");
            }
            if (taskStartDate.compareTo(taskEndDate) > 0) {
                LOGGER.error("The Task Start Date ( " + task.getStartDate() + " ) should not be greater then the Task End Date ( " + task.getEndDate() + " )");
                throw new ValidationException("The Task End Date ( " + simpleDateFormat.format(task.getEndDate()) + " ) should be after the Task Start Date ( " + simpleDateFormat.format(task.getStartDate()) + " )");
            }

        }
    }

    private Task addEmployee(Task task) {
//      set employee
        Set<Employee> employees = new HashSet<>(task.getEmployees());
        employees.forEach(employee -> task.addEmployee(employee));
        return task;
    }


    private Task updateActualDate(Task task) {
        if (task.getStartDate() != null && task.getEndDate() != null && task.getStartedOn() != null)
            task.setEndedOn(new Date(System.currentTimeMillis()));
        return task;
    }


}
