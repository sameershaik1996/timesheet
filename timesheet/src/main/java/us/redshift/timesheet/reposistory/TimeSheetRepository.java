package us.redshift.timesheet.reposistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.TimeSheet;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {
    Page<TimeSheet> findAll(Pageable pageable);

}
