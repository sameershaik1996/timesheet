package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.TimeSheet;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {
}
