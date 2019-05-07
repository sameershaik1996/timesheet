package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
//        Set<Project> projectSet = new HashSet<>(client.getProjects());
//        projectSet.forEach(project -> client.add(project));
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client){
        if(!clientRepository.existsById(client.getId()))
            throw  new ResourceNotFoundException("Client", "Id", client.getId());
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClient() {
        List<Client> clients = new ArrayList<>();
        clientRepository.findAll().iterator().forEachRemaining(clients::add);
        return clients;
    }

    @Override
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "Id", id));
    }


}
