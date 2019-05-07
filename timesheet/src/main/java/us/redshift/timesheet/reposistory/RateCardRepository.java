package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.RateCard;

public interface RateCardRepository extends JpaRepository<RateCard, Long> {


}
