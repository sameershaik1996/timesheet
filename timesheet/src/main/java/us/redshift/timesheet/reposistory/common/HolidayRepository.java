package us.redshift.timesheet.reposistory.common;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.EmployeeRole;
import us.redshift.timesheet.domain.common.HolidayList;

public interface HolidayRepository extends JpaRepository<HolidayList, Long> {

}
