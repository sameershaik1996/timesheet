package us.redshift.timesheet.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.ProjectStatus;
import us.redshift.timesheet.domain.RateCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.ClientRepository;
import us.redshift.timesheet.reposistory.ProjectRepository;
import us.redshift.timesheet.util.Reusable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper mapper;


    public ProjectService(ProjectRepository projectRepository, ClientRepository clientRepository, ModelMapper mapper) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
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
    public Project updateProject(Project project) {
        if (!projectRepository.existsById(project.getId()))
            throw new ResourceNotFoundException("Project", "Id", project.getId());
        project = setRateCardDetail(project);
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectId(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));

    }

    @Override
    public List<Project> getAllProjectByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return projectRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Project> getClientProjectsByPagination(Long clientId, int page, int limits, String orderBy, String... fields) {
        if (!clientRepository.existsById(clientId))
            throw new ResourceNotFoundException("Project", "ClientId", clientId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return projectRepository.findProjectsByClient_Id(clientId, pageable).getContent();
    }

    @Override
    public List<Project> findAllByEmployeeId(Long employeeId) {
        return projectRepository.findAllByEmployeeId(employeeId);
    }

    @Override
    public ProjectStatus[] getAllProjectStatus() {
        return ProjectStatus.values();
    }


    private Project setRateCardDetail(Project project) {
        Set<RateCardDetail> rateCardDetails = new HashSet<>();
        if (project.getRateCard() != null)
            rateCardDetails = new HashSet<>(project.getRateCard().getRateCardDetails());
        rateCardDetails.forEach(rateCardDetail -> project.getRateCard().addRateCardDetail(rateCardDetail));
        return project;
    }

}
