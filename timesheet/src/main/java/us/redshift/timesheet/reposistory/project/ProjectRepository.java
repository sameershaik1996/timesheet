package us.redshift.timesheet.reposistory.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    Page<Project> findAll(Pageable pageable);

    Page<Project> findProjectsByClient_Id(Long clientId, Pageable pageable);

    List<Project> findAllByEmployeeIdAndStatusOrderByIdAsc(Long employeeId, ProjectStatus status);

    List<Project> findAllByManagerId(Long mangerId);

    List<Project> findAllByStatusOrderByIdAsc(ProjectStatus status);


}
