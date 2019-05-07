package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.TaskCardDetail;

import java.util.Date;
import java.util.List;

public interface TaskCardDetailRepository extends JpaRepository<TaskCardDetail, Long> {

    List<TaskCardDetail> findByDateBetweenAndTaskCardId(Date start, Date end,Long taskCardId);

}
