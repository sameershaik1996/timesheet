package us.redshift.timesheet.service.client;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;
import us.redshift.timesheet.domain.client.Poc;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.client.ClientRepository;
import us.redshift.timesheet.util.Reusable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Client> getAllClientByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<Client> projectList = clientRepository.findAll(pageable).getContent();
        Set<Client> clientSet = projectList.stream().collect(Collectors.toCollection(HashSet::new));
        return clientSet;
    }

    @Override
    public ClientStatus[] getAllClientStatus() {
        return ClientStatus.values();
    }
}
