package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.FocusArea;

public interface FocusAreaRepository extends JpaRepository<FocusArea, Long> {


}
