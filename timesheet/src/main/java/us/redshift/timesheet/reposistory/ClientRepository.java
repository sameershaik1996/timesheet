package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
