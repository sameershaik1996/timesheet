package us.redshift.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.common.EmployeeStatus;

import java.util.List;

public interface EmployeeRespository extends JpaRepository<Employee, Long> {

    Page<Employee> findAll(Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE emp_employees SET status=?1 WHERE id in ?2", nativeQuery = true)
    int setStatusForEmployee(String status, List<Long> id);


    List<Employee> findByIdIn(List<Long> id);

    Employee findTopByOrderByIdDesc();


    List<Employee> findByIdNotLike(Long id);


}
