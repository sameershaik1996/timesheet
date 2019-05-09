package us.redshift.timesheet.reposistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.Project;
import us.redshift.timesheet.domain.ProjectStatus;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    Page<Project> findAll(Pageable pageable);

    Page<Project> findProjectsByClient_Id(Long clientId, Pageable pageable);

    List<Project> findAllByEmployeeIdAndStatus(Long employeeId, ProjectStatus status);


}
