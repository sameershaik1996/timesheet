package us.redshift.timesheet.service.common;

import us.redshift.timesheet.domain.EmployeeRole;

import java.util.List;

public interface IEmployeeRoleService {

    EmployeeRole saveEmployeeRole(EmployeeRole employeeRole);

    EmployeeRole UpdateEmployeeRole(EmployeeRole employeeRole);

    List<EmployeeRole> getAllEmployeeRole();

    EmployeeRole getEmployeeRoleById(Long id);

}
