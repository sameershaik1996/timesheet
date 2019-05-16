package us.redshift.timesheet.reposistory.timesheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.timesheet.TimeOff;

@Repository
public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {


}
