package us.redshift.timesheet.reposistory.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.client.ClientStatus;

import java.util.Set;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Set<Client> findAllByStatusOrderByIdAsc(ClientStatus status);

}
