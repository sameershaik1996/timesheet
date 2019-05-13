package us.redshift.timesheet.service.client;

import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;

import java.util.Set;


public interface IClientService {

    Client saveClient(Client client);

    Client updateClient(Client client);

    Client getClientById(Long id);

    Set<Client> getAllClientByPagination(int page, int limits, String orderBy, String... fields);


    ClientStatus[] getAllClientStatus();
}
