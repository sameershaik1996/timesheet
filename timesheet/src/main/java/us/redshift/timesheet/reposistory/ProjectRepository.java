package us.redshift.timesheet.reposistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    Page<Project> findAll(Pageable pageable);

    Page<Project> findProjectsByClient_Id(Long clientId, Pageable pageable);

    List<Project> findAllByEmployeeId(Long employeeId);


}
