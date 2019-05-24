package us.redshift.timesheet.reposistory.timesheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.Date;
import java.util.Set;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {


    TimeSheet findFirstByStatus(TimeSheetStatus status);

    TimeSheet findTimeSheetByEmployeeIdAndYearAndWeekNumberOrderByTaskCardsAsc(Long employeeId, int year, int weekNumber);

    TimeSheet findFirstByEmployeeIdAndStatusOrderByFromDateDesc(Long employeeId, TimeSheetStatus status);

    Set<TimeSheet> findAllByTaskCardsInAndStatusNotLikeOrderByFromDateAsc(Set<TaskCard> taskCardSet, TimeSheetStatus status);


    @Transactional
    @Modifying
    @Query(value = "UPDATE pss_timesheets SET status=?1 WHERE id = ?2", nativeQuery = true)
    int setStatusForTimeSheet(String status, Long id);


    Set<TimeSheet> findAllByStatusAndFromDateBefore(TimeSheetStatus status, Date localDate);


}
