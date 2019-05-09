package us.redshift.timesheet.reposistory;


import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
