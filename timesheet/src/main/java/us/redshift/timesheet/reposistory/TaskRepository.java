package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
