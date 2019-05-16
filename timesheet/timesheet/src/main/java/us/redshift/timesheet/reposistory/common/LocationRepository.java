package us.redshift.timesheet.reposistory.common;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.common.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
