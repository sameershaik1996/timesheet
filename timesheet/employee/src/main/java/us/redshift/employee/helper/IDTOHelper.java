package us.redshift.employee.helper;

import us.redshift.employee.domain.Employee;
import us.redshift.employee.dto.EmployeeDto;

public interface IDTOHelper {

    EmployeeDto employeToDto(Employee employee);

    Employee employeeToEntity(EmployeeDto employeeDto);


}
