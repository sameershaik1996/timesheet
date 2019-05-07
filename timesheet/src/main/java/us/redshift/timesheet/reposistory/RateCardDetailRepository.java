package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.RateCardDetail;

public interface RateCardDetailRepository extends JpaRepository<RateCardDetail, Long> {

//    RateCardDetail findByRatecardIdAndLocationIdAndSkillIdAndDesignationId(Long rateCardId, Long locationId, Long skillId, Long designationId);
}
