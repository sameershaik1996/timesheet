package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.RateCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.ClientRepository;
import us.redshift.timesheet.reposistory.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

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
    public Project saveProject(Long clientId, Project project) {
//        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client", "Id", clientId));
//        project.setClient(client);
        Client client = new Client();
        client.setId(clientId);
        project.setClient(client);
        project = setRateCardDetail(project);
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        projectRepository.findById(project.getId()).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", project.getId()));
        return projectRepository.save(project);

    }

    @Override
    public List<Project> getAllProject() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().iterator().forEachRemaining(projects::add);
        return projects;
    }

    @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));

    }

    @Override
    public List<Project> getClientProject(Long clientId) {
        return projectRepository.findProjectsByClient_Id(clientId);

    }


    private Project setRateCardDetail(Project project) {
        List<RateCardDetail> rateCardDetails = new ArrayList<>();
        if (project.getRateCard() != null)
            rateCardDetails = new ArrayList<>(project.getRateCard().getRateCardDetails());

        rateCardDetails.forEach(rateCardDetail -> project.getRateCard().addRateCardDetail(rateCardDetail));
        return project;
    }

}
