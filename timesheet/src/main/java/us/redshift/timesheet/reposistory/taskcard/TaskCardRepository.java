package us.redshift.timesheet.reposistory.taskcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.timesheet.domain.taskcard.TaskCard;

@Repository
public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {


    Page<TaskCard> findAll(Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE pss_task_cards SET status=?1 WHERE id = ?2", nativeQuery = true)
    int setStatusForTaskCard(String status, Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE pss_task_cards SET time_sheet_id=?1 WHERE id = ?2", nativeQuery = true)
    int setTimeSheetIdForTaskCard(Long timeSheetId, Long id);
}
