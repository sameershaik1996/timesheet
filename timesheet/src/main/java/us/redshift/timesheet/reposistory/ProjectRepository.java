package us.redshift.timesheet.reposistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;

import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    Page<Project> findAll(Pageable pageable);

    Page<Project> findProjectsByClient_Id(Long clientId, Pageable pageable);

    Set<Project> findAllByEmployeeIdAndStatus(Long employeeId, ProjectStatus status);


}
