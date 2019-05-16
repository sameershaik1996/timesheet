package us.redshift.timesheet.reposistory.taskcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;

@Repository
public interface TaskCardDetailRepository extends JpaRepository<TaskCardDetail, Long> {

    Page<TaskCardDetail> findTaskCardDetailsByTaskCard_Id(Long taskCardId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE pss_task_card_details SET status=?1 WHERE task_card_id = ?2", nativeQuery = true)
    int setStatusForTaskCardDetail(String status, Long taskCardId);

}
