package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Client;
import us.redshift.timesheet.exception.ResourceNotFoundException;

import java.util.List;


public interface IClientService {

    Client saveClient(Client client);

    Client updateClient(Client client);

    List<Client> getAllClient();

    Client getClient(Long id);

}
