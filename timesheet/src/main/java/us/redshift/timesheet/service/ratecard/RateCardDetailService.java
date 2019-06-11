package us.redshift.timesheet.service.ratecard;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.reposistory.ratecard.RateCardDetailRepository;

@Service
public class RateCardDetailService implements IRateCardDetailService {

    private final RateCardDetailRepository rateCardDetailRepository;

    public RateCardDetailService(RateCardDetailRepository rateCardDetailRepository) {
        this.rateCardDetailRepository = rateCardDetailRepository;
    }

    @Override
    public RateCardDetail findByRateCard_IdAndLocation_IdAndEmployeeRole_IdAndDesignationId(Long rateCardId, Long locationId, Long roleId, Long designationId) {
        return /*rateCardDetailRepository.findByRateCard_IdAndLocation_IdAndEmployeeRole_IdAndDesignationId(rateCardId, locationId, roleId, designationId)*/null;
    }

    @Override
    public RateCardDetail findByRateCard_IdAndLocation_IdAndEmployeeRole_Id(Long rateCardId, Long locationId, Long roleId) {
        return rateCardDetailRepository.findByRateCard_IdAndLocation_IdAndEmployeeRole_Id(rateCardId, locationId, roleId);
    }

//    @Override
//    public RateCardDetail findByLocationIdAndSkillIdAndDesignationId(Long locationId, Long skillId, Long designationId) {
//        return rateCardDetailRepository.findByLocationIdAndSkillIdAndDesignationId(locationId, skillId, designationId);
//    }
//
//    @Override
//    public RateCardDetail findByRateCard_ProjectTypeAndLocationIdAndSkillIdAndDesignationId(ProjectType type, Long locationId, Long skillId, Long designationId) {
//        return rateCardDetailRepository.findByRateCard_ProjectTypeAndLocationIdAndSkillIdAndDesignationId(type, locationId, skillId, designationId);
//    }
}
