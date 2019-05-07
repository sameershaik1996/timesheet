package us.redshift.timesheet.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.domain.ClientStatus;
import us.redshift.timesheet.domain.Poc;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.ClientRepository;
import us.redshift.timesheet.util.Reusable;

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
    public List<Client> getAllClientByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return clientRepository.findAll(pageable).getContent();
    }

    @Override
    public ClientStatus[] getAllClientStatus() {
        return ClientStatus.values();
    }
}
