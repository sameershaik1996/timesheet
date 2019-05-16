package us.redshift.timesheet.reposistory.client;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.client.FocusArea;

public interface FocusAreaRepository extends JpaRepository<FocusArea, Long> {


}
