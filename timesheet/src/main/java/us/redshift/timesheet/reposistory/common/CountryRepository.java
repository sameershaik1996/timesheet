package us.redshift.timesheet.reposistory.common;


import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.common.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
