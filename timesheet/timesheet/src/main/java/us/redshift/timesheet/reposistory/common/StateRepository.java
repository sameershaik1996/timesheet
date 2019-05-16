package us.redshift.timesheet.reposistory.common;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.common.Country;
import us.redshift.timesheet.domain.common.State;

import java.util.Set;

public interface StateRepository extends JpaRepository<State, Long> {

    Set<State> findByCountry(Country country);
}
