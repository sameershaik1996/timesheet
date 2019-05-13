package us.redshift.timesheet.reposistory.ratecard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.ratecard.RateCard;

@Repository
public interface RateCardRepository extends JpaRepository<RateCard, Long> {

}
