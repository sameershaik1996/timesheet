package us.redshift.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.employee.domain.common.Country;
import us.redshift.employee.domain.common.State;


import java.util.Set;

public interface StateRepository extends JpaRepository<State,Long> {

    Set<State> findByCountry(Country country);
}
