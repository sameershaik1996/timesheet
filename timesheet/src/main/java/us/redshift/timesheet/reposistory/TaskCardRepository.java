package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import us.redshift.timesheet.domain.TaskCard;
import us.redshift.timesheet.domain.TimeSheetStatus;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {

    @Modifying
    @Query(value = "SELECT * FROM Task_Cards t join Task_card_Details t1 on t.id=t1.task_card_id where t1.date between ? and ? group by t.id ",nativeQuery = true)
    public List<TaskCard> findByDateBetween(Instant start, Instant end);

    //public List<TaskCard> setStatus(TimeSheetStatus status);

}
