package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.domain.ClientStatus;

import java.util.List;


public interface IClientService {

    Client saveClient(Client client);

    Client updateClient(Client client);

    Client getClientById(Long id);

    List<Client> getAllClientByPagination(int page, int limits, String orderBy, String... fields);


    ClientStatus[] getAllClientStatus();
}
