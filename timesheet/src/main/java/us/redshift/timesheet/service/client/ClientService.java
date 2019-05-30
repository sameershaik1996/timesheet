package us.redshift.timesheet.service.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;
import us.redshift.timesheet.domain.client.Poc;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.client.ClientRepository;
import us.redshift.timesheet.util.Reusable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }


    @Override
    public Client saveClient(Client client) {

        Set<Poc> pocs = new HashSet<>(client.getPocs());
        pocs.forEach(poc -> {
            client.addPoc(poc);
        });
        return clientRepository.save(client);
    }

    @Override
    public List<Client> updateClient(List<Client> clients, ClientStatus status) {
        List<Client> clientList = new ArrayList<>();
        clients.forEach(client -> {
            if (status != null) {
                Client getClient = clientRepository.findById(client.getId()).orElseThrow(() -> new ResourceNotFoundException("Client", "Id", client.getId()));
                getClient.setStatus(status);
                clientList.add(getClient);
            }
        });

//        Set<Client> clientSet = clientRepository.saveAll(clientList).stream().collect(Collectors.toCollection(HashSet::new));
        return clientRepository.saveAll(clientList);
    }

    @Override
    public Client updateClient(Client client) {
        if (!clientRepository.existsById(client.getId()))
            throw new ResourceNotFoundException("Client", "Id", client.getId());
        Set<Poc> pocs = new HashSet<>(client.getPocs());
        pocs.forEach(poc -> {
            client.addPoc(poc);
        });

        return clientRepository.save(client);

    }


    @Override
    public Client getClientById(Long id) {
        if (!clientRepository.existsById(id))
            throw new ResourceNotFoundException("Client", "Id", id);
        return clientRepository.findById(id).get();
    }

    @Override
    public Page<Client> getAllClientByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        Page<Client> projectList = clientRepository.findAll(pageable);
//        Set<Client> clientSet = projectList.stream().collect(Collectors.toCollection(HashSet::new));
        return projectList;
    }

    @Override
    public ClientStatus[] getAllClientStatus() {
        return ClientStatus.values();
    }

    @Override
    public List<Client> findAllByStatus(ClientStatus status) {
        return clientRepository.findAllByStatusOrderByIdAsc(status);
    }

    @Override
    public Boolean existsById(Long ClientId) {
        return clientRepository.existsById(ClientId);
    }
}
