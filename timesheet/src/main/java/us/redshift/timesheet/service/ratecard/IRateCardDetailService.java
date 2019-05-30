package us.redshift.timesheet.service.ratecard;

import us.redshift.timesheet.domain.ratecard.RateCardDetail;

public interface IRateCardDetailService {

    RateCardDetail findByLocationIdAndSkillIdAndDesignationId(Long locationId, Long skillId, Long designationId);
}
