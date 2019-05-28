package us.redshift.timesheet.reposistory.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByStatusOrderByIdAsc(ClientStatus status);

    Client findByClientCode(String clientCode);

}
