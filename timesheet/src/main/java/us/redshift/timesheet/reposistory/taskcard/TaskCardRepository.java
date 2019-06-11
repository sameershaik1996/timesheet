package us.redshift.timesheet.reposistory.taskcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.List;

@Repository
public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {


    Page<TaskCard> findAll(Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE pss_task_cards SET status=?1 WHERE time_sheet_id = ?2", nativeQuery = true)
    int setStatusForTaskCard(String status, Long id);


    List<TaskCard> findByStatusNotLikeAndProjectIn(TimeSheetStatus status, List<Project> projectSet);

    List<TaskCard> findByStatusAndType(TimeSheetStatus status, TaskType taskType);

    List<TaskCard> findByStatusNotLikeAndProject_IdOrderByEmployeeIdAscTaskCardDetailsAsc(TimeSheetStatus status, Long projectId);


    List<TaskCard> findAllByStatusNotLikeAndProject_IdAndApproverIdOrderByEmployeeIdAscTaskCardDetailsAsc(TimeSheetStatus status, Long projectId, Long approverId);


}
