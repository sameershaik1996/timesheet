package us.redshift.timesheet.service.client;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;

import java.util.Set;


public interface IClientService {

    Client saveClient(Client client);

    Set<Client> updateClient(Set<Client> clients, ClientStatus status);

    Client getClientById(Long id);

    Page<Client> getAllClientByPagination(int page, int limits, String orderBy, String... fields);

    ClientStatus[] getAllClientStatus();

    Set<Client> findAllByStatus(ClientStatus status);

}
