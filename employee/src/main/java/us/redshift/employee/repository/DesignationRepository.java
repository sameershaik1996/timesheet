package us.redshift.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.employee.domain.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

}
