package us.redshift.timesheet.service.client;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;

import java.util.List;


public interface IClientService {

    Client saveClient(Client client);

    List<Client> updateClient(List<Client> clients, ClientStatus status);

    Client updateClient(Client client);

    Client getClientById(Long id);

    Page<Client> getAllClientByPagination(int page, int limits, String orderBy, String... fields);

    ClientStatus[] getAllClientStatus();

    List<Client> findAllByStatus(ClientStatus status);

    Boolean existsById(Long ClientId);

    Page<Client> getAllClientsBySearchFilter(String search,Integer page, Integer limits, String orderBy, String[] fields);

    List<Long> getClientByName(String name);
}
