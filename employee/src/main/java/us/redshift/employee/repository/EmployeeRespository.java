package us.redshift.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.common.EmployeeStatus;

import java.util.Date;
import java.util.List;

public interface EmployeeRespository extends JpaRepository<Employee, Long> {

    Page<Employee> findAll(Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE emp_employees SET status=?1 WHERE id in ?2", nativeQuery = true)
    int setStatusForEmployee(String status, List<Long> id);


    @Query(value = "SELECT * FROM emp_employees  WHERE (COALESCE(first_name,'') LIKE %:firstName% or COALESCE(last_name,'') LIKE %:lastName% or COALESCE(employee_id,'') LIKE %:employeeId%) or COALESCE(designation_id,'') in(:designationIds)  or COALESCE(id,'') in(:empIds)", nativeQuery = true)
    Page<Employee> searchEmployeePaged(@Param("firstName") String firstName, @Param("lastName") String lastName,@Param("employeeId")String employeeId,@Param("designationIds")List<Long> designationIds,@Param("empIds")List<Long> empIds,Pageable pageable);

    @Query(value = "SELECT distinct(id) FROM emp_employees  WHERE (COALESCE(first_name,'') LIKE %:firstName% or COALESCE(last_name,'') LIKE %:lastName% or COALESCE(employee_id,'') LIKE %:employeeId%) or COALESCE(designation_id,'') in(:designationIds) or COALESCE(id,'') in(:empIds)", nativeQuery = true)
    List<Long> searchEmployee(@Param("firstName") String firstName, @Param("lastName") String lastName,@Param("employeeId")String employeeId,@Param("designationIds")List<Long> designationIds,@Param("empIds")List<Long> empIds);



    List<Employee> findByIdIn(List<Long> id);

    Employee findTopByOrderByIdDesc();


    List<Employee> findByIdNotLike(Long id);

    List<Employee> findByLastWorkingDateLessThanAndStatus(Date date,EmployeeStatus status);

}
