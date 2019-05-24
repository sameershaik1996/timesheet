package us.redshift.employee.service;

import org.springframework.stereotype.Service;
import us.redshift.employee.domain.Employee;

import java.sql.SQLException;
import java.util.List;


public interface IEmployeeService {

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployee();

    Boolean checkIfEmployeeExists(Long id);

    List<Employee> getEmployeeByIds(List<Long> id);
}
