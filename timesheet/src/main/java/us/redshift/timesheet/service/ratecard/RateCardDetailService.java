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
    public RateCardDetail findByLocationIdAndSkillIdAndDesignationId(Long locationId, Long skillId, Long designationId) {
        return rateCardDetailRepository.findByLocationIdAndSkillIdAndDesignationId(locationId, skillId, designationId);
    }
}
