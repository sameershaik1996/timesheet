package us.redshift.timesheet.reposistory.timesheet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {


    TimeSheet findFirstByStatus(TimeSheetStatus status);

    List<TimeSheet> findTimeSheetByEmployeeIdAndYearAndWeekNumberOrderByTaskCardsAsc(Long employeeId, int year, int weekNumber);

    List<TimeSheet> findTimeSheetByEmployeeIdAndYearAndWeekNumberAndStatusOrderByTaskCardsAsc(Long employeeId, int year, int weekNumber,TimeSheetStatus status);

    //List<TimeSheet> findTimeSheetByEmployeeIdAndYearAndWeekNumberAndStatusOrderByTaskCardsAsc(Long employeeId, int year, int weekNumber,TimeSheetStatus status);

    TimeSheet findFirstByEmployeeIdAndStatusOrderByFromDateAsc(Long employeeId, TimeSheetStatus status);

    //List<TimeSheet> findByEmployeeIdAndStatusOrderByFromDateAsc(Long employeeId, TimeSheetStatus status);

    Page<TimeSheet> findAllByTaskCardsInAndStatusNotLikeOrderByFromDateAsc(Set<TaskCard> taskCardSet, TimeSheetStatus status, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE pss_timesheets SET status=?1 WHERE id = ?2", nativeQuery = true)
    int setStatusForTimeSheet(String status, Long id);


    Set<TimeSheet> findAllByStatusAndFromDateBefore(TimeSheetStatus status, Date date);

    Set<TimeSheet> findAllByStatusAndFromDateLessThanAndEmployeeId(TimeSheetStatus status, Date date, Long employeeId);


}
