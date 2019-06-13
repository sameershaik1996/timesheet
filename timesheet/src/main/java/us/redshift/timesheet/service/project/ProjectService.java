package us.redshift.timesheet.service.project;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.project.ProjectRepository;
import us.redshift.timesheet.service.client.IClientService;
import us.redshift.timesheet.service.task.ITaskService;
import us.redshift.timesheet.util.Reusable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final IClientService clientService;
    private final ITaskService taskService;


    public ProjectService(ProjectRepository projectRepository,
                          @Lazy IClientService clientService,
                          @Lazy ITaskService taskService) {
        this.projectRepository = projectRepository;
        this.clientService = clientService;
        this.taskService = taskService;
    }

    @Override
    public Project saveProject(Project project) {
        project = setRateCardDetail(project);
        return projectRepository.save(project);
    }

    @Override
    public List<Project> updateProject(List<Project> projects, ProjectStatus status) {


        List<Project> projectList = new ArrayList<>();
        projects.forEach(project -> {
            if (status != null) {
                Project getProject = projectRepository.findById(project.getId()).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", ""));
                if (ProjectStatus.COMPLETE.equals(status))
                    getProject = updateActualDate(project);

                getProject.setStatus(status);
                projectList.add(getProject);
            }
        });

//        Set<Project> projectSet = projectRepository.saveAll(projectList).stream().collect(Collectors.toCollection(HashSet::new));

        return projectRepository.saveAll(projectList);
    }

    @Override
    public Project updateProject(Project project) {
        if (!projectRepository.existsById(project.getId()))
            throw new ResourceNotFoundException("Project", "Id", project.getId());
        if (ProjectStatus.COMPLETE.equals(project.getStatus()))
            project = updateActualDate(project);
        project = setRateCardDetail(project);
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));

    }

    @Override
    public Page<Project> getAllProjectByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
//        List<Project> projectList = projectRepository.findAll(pageable).getContent();
//        Set<Project> projectSet = projectList.stream().collect(Collectors.toCollection(HashSet::new));
        return projectRepository.findAll(pageable);
    }

    @Override
    public Page<Project> getClientProjectsByPagination(Long clientId, int page, int limits, String orderBy, String... fields) {
        if (!clientService.existsById(clientId))
            throw new ResourceNotFoundException("Project", "ClientId", clientId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
//        List<Project> projectList = projectRepository.findProjectsByClient_Id(clientId, pageable).getContent();
//        Set<Project> projectSet = projectList.stream().collect(Collectors.toCollection(HashSet::new));
        return projectRepository.findProjectsByClient_Id(clientId, pageable);
    }

    @Override
    public List<Project> findAllByEmployeeId(Long employeeId, ProjectStatus status) {
        Set<Project> projects = new LinkedHashSet<>(projectRepository.findAllByEmployeeIdAndStartDateLessThanEqualAndStatusOrderByIdAsc(employeeId, new Date(System.currentTimeMillis()), status));
        return projects.stream().collect(Collectors.toList());
    }


    @Override
    public List<Long> findAllEmployeesByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId))
            throw new ResourceNotFoundException("Project", "Id", projectId);
        List<Long> ids = projectRepository.findById(projectId).get().getEmployeeId();
        return ids;
    }


    @Override
    public ProjectStatus[] getAllProjectStatus() {
        return ProjectStatus.values();
    }

    @Override
    public List<Project> findAllByStatus(ProjectStatus status) {
        return projectRepository.findAllByStatusOrderByIdAsc(status);
    }

    @Override
    public Boolean existsById(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    @Override
    public List<Project> getAllProjectByManagerId(Long managerId) {
        return projectRepository.findAllByManagerId(managerId);
    }

    private Project setRateCardDetail(Project project) {
        Set<RateCardDetail> rateCardDetails = new HashSet<>();
        if (project.getRateCard() != null)
            rateCardDetails = new HashSet<>(project.getRateCard().getRateCardDetails());
        rateCardDetails.forEach(rateCardDetail -> project.getRateCard().addRateCardDetail(rateCardDetail));
        return project;
    }

    private Project updateActualDate(Project project) {
        if (project.getStartDate() != null && project.getEndDate() != null && project.getStartedOn() != null) {

            List<Task> tasks = taskService.getProjectTasksByPagination(project.getId(), 0, 0, null, null).getContent();

            Long count = tasks.stream().filter(task -> TaskStatus.COMPLETE.equals(task.getStatus())).count();

            //////System.out.println(count);

            if (tasks.size() == count)
                project.setEndedOn(new Date(System.currentTimeMillis()));
            else
                throw new ValidationException("Some Task under this project still not completed");
        }
        return project;
    }

}
