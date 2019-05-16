package us.redshift.timesheet.reposistory.client;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.client.Industry;

public interface IndustryRepository extends JpaRepository<Industry, Long> {


}
