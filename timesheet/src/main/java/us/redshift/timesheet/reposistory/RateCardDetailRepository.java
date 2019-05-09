package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.RateCardDetail;

@Repository
public interface RateCardDetailRepository extends JpaRepository<RateCardDetail, Long> {


    RateCardDetail findByRateCard_IdAndLocationIdAndSkillIdAndDesignationId(Long rateCardId, Long locationId, Long skillId, Long designationId);

}
