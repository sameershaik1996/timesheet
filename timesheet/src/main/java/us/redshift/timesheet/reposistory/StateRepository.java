package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.Country;
import us.redshift.timesheet.domain.State;

import java.util.Set;

public interface StateRepository extends JpaRepository<State, Long> {

    Set<State> findByCountry(Country country);
}
