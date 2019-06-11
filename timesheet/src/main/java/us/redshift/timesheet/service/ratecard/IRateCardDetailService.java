package us.redshift.timesheet.service.ratecard;

import us.redshift.timesheet.domain.ratecard.RateCardDetail;

public interface IRateCardDetailService {

    //    RateCardDetail findByLocationIdAndSkillIdAndDesignationId(Long locationId, Long skillId, Long designationId);
//    RateCardDetail findByRateCard_ProjectTypeAndLocationIdAndSkillIdAndDesignationId(ProjectType type, Long locationId, Long skillId, Long designationId);
    RateCardDetail findByRateCard_IdAndLocation_IdAndEmployeeRole_IdAndDesignationId(Long rateCardId, Long locationId, Long roleId, Long designationId);

    RateCardDetail findByRateCard_IdAndLocation_IdAndEmployeeRole_Id(Long rateCardId, Long locationId, Long roleId);
}
