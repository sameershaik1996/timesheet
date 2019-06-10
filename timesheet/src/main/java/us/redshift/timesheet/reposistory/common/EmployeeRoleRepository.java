package us.redshift.timesheet.reposistory.common;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.EmployeeRole;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {
}
