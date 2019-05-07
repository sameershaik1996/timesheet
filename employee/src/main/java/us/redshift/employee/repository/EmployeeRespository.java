package us.redshift.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.employee.domain.Employee;

import java.util.List;

public interface EmployeeRespository extends JpaRepository<Employee, Long> {

    List<Employee> findByIdIn(List<Long> id);

}
