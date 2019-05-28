package us.redshift.timesheet.reposistory.ratecard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.timesheet.domain.project.ProjectType;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;

import java.util.List;

@Repository
public interface RateCardDetailRepository extends JpaRepository<RateCardDetail, Long> {


    RateCardDetail findByRateCard_IdAndLocationIdAndSkillIdAndDesignationId(Long rateCardId, Long locationId, Long skillId, Long designationId);

    List<RateCardDetail> findAllByRateCard_ProjectTypeAndRateCard_IsDefaultOrderByLocation(ProjectType type, Boolean flag);

    RateCardDetail findByLocationIdAndSkillIdAndDesignationId(Long locationId, Long skillId, Long designationId);

}
