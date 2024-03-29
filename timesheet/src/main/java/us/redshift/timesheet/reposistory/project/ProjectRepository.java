package us.redshift.timesheet.reposistory.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.project.ProjectStatus;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    Page<Project> findAll(Pageable pageable);

    Page<Project> findProjectsByClient_Id(Long clientId, Pageable pageable);

    List<Project> findAllByEmployeeIdAndStatusOrderByIdAsc(Long employeeId, ProjectStatus status);

    List<Project> findAllByManagerId(Long mangerId);

    List<Project> findAllByStatusOrderByIdAsc(ProjectStatus status);


    List<Project> findAllByEmployeeIdAndStartDateLessThanEqualAndStatusOrderByIdAsc(Long employeeId, Date date, ProjectStatus status);

    List<Project> findAllByEmployeeIdAndStartDateLessThanEqualAndStartDateLessThanEqualAndStatusOrderByIdAsc(Long employeeId, Date currentDate, Date timeSheetFromDate, ProjectStatus status);


    @Query(value = "select * from pss_projects  WHERE (COALESCE(project_code,'') LIKE %:projectCode% OR COALESCE(name,'') LIKE %:name%) or COALESCE(client_id,'') in (:clientIds) or COALESCE(manager_id,'') in (:managerIds)",nativeQuery = true)
    Page<Project> searchProjects(@Param("projectCode")String projectCode,@Param("name") String name,@Param("clientIds") List<Long> clientIds,@Param("managerIds") List<Long> managerIds,Pageable pageable);

    @Query(value = "select distinct(id) from pss_projects  WHERE (COALESCE(project_code,'') LIKE %:projectCode% OR COALESCE(name,'') LIKE %:name%) or COALESCE(client_id,'') in (:clientIds) or COALESCE(manager_id,'') in (:managerIds)",nativeQuery = true)
    List<Long> searchProjectsList(@Param("projectCode")String projectCode,@Param("name") String name,@Param("clientIds") List<Long> clientIds,@Param("managerIds") List<Long> managerIds);


}