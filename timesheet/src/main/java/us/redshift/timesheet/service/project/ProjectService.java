package us.redshift.timesheet.service.project;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.client.ClientRepository;
import us.redshift.timesheet.reposistory.project.ProjectRepository;
import us.redshift.timesheet.util.Reusable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;


    public ProjectService(ProjectRepository projectRepository, ClientRepository clientRepository) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Project saveProject(Project project) {
        project = setRateCardDetail(project);
        return projectRepository.save(project);
    }

    @Override
    public Project saveProjectByClientId(Long clientId, Project project) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Project", "ClientId", clientId));
        project.setClient(client);
        project = setRateCardDetail(project);
        return projectRepository.save(project);
    }


    @Override
    public Set<Project> updateProject(Set<Project> projects, ProjectStatus status) {


        List<Project> projectList = new ArrayList<>();
        projects.forEach(project -> {
            if (status != null) {
                Project getProject = projectRepository.findById(project.getId()).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", ""));
                getProject.setStatus(status);
                projectList.add(getProject);
            } else {
                if (!projectRepository.existsById(project.getId()))
                    throw new ResourceNotFoundException("Project", "Id", project.getId());
                project = setRateCardDetail(project);
                projectList.add(project);
            }
        });

        Set<Project> projectSet = projectRepository.saveAll(projectList).stream().collect(Collectors.toCollection(HashSet::new));

        return projectSet;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));

    }

    @Override
    public Set<Project> getAllProjectByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<Project> projectList = projectRepository.findAll(pageable).getContent();
        Set<Project> projectSet = projectList.stream().collect(Collectors.toCollection(HashSet::new));
        return projectSet;
    }

    @Override
    public Set<Project> getClientProjectsByPagination(Long clientId, int page, int limits, String orderBy, String... fields) {
        if (!clientRepository.existsById(clientId))
            throw new ResourceNotFoundException("Project", "ClientId", clientId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<Project> projectList = projectRepository.findProjectsByClient_Id(clientId, pageable).getContent();
        Set<Project> projectSet = projectList.stream().collect(Collectors.toCollection(HashSet::new));
        return projectSet;
    }

    @Override
    public Set<Project> findAllByEmployeeId(Long employeeId, ProjectStatus status) {
        return projectRepository.findAllByEmployeeIdAndStatusOrderByIdAsc(employeeId, status);
    }


    @Override
    public Set<Long> findAllEmployeesByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId))
            throw new ResourceNotFoundException("Project", "Id", projectId);
        Set<Long> ids = projectRepository.findById(projectId).get().getEmployeeId();
        return ids;
    }


    @Override
    public ProjectStatus[] getAllProjectStatus() {
        return ProjectStatus.values();
    }

    @Override
    public Set<Project> findAllByStatus(ProjectStatus status) {
        return projectRepository.findAllByStatusOrderByIdAsc(status);
    }


    private Project setRateCardDetail(Project project) {
        Set<RateCardDetail> rateCardDetails = new HashSet<>();
        if (project.getRateCard() != null)
            rateCardDetails = new HashSet<>(project.getRateCard().getRateCardDetails());
        rateCardDetails.forEach(rateCardDetail -> project.getRateCard().addRateCardDetail(rateCardDetail));
        return project;
    }

}
