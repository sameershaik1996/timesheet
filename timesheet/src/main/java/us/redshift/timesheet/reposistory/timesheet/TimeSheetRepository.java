package us.redshift.timesheet.reposistory.timesheet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {
    Page<TimeSheet> findAll(Pageable pageable);


    TimeSheet findFirstByStatus(TimeSheetStatus status);

    TimeSheet findTimeSheetByEmployeeIdAndYearAndWeekNumber(Long employeeId, int year, int weekNumber);

    TimeSheet findFirstByEmployeeIdAndStatusOrderByFromDateDesc(Long employeeId, TimeSheetStatus status);


}
