package us.redshift.timesheet.service.project;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.project.ProjectRepository;
import us.redshift.timesheet.service.client.IClientService;
import us.redshift.timesheet.util.Reusable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final IClientService clientService;


    public ProjectService(ProjectRepository projectRepository,
                          @Lazy IClientService clientService) {
        this.projectRepository = projectRepository;
        this.clientService = clientService;
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
        return projectRepository.findAllByEmployeeIdAndStatusOrderByIdAsc(employeeId, status);
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

}
