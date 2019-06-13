package us.redshift.timesheet.reposistory.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.task.TaskStatus;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAll(Pageable pageable);

    Page<Task> findTaskByProject_Id(Long taskId, Pageable pageable);

    List<Task> findAllByStatusOrderByIdAsc(TaskStatus status);

    List<Task> findAllByProject_IdAndEmployees_EmployeeIdAndStatusOrderByIdAsc(Long projectId, Long employeeId, TaskStatus status);


    List<Task> findAllByProject_IdAndEmployees_EmployeeIdAndStartDateLessThanEqualAndStatusOrderByIdAsc(Long projectId, Long employeeId, Date today, TaskStatus status);


}
