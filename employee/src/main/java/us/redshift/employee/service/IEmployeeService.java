package us.redshift.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.common.EmployeeStatus;

import java.sql.SQLException;
import java.util.List;


public interface IEmployeeService {

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    Page<Employee> getAllEmployee(int page, int limits, String orderBy, String... fields);

    Boolean checkIfEmployeeExists(Long id);

    List<Employee> getEmployeeByIds(List<Long> id);

    int setStatusForEmployee(EmployeeStatus status, List<Long> empIds);
}
