package us.redshift.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.employee.domain.common.Country;

public interface CountryRepository extends JpaRepository<Country,Long> {
}
