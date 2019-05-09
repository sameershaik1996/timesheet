package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.Industry;

public interface IndustryRepository extends JpaRepository<Industry, Long> {


}
