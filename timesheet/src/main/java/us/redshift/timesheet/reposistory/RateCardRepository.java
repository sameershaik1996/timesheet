package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.RateCard;

@Repository
public interface RateCardRepository extends JpaRepository<RateCard, Long> {

}
