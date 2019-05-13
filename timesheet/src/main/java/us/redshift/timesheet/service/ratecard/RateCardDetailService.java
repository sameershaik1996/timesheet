package us.redshift.timesheet.service.ratecard;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.reposistory.ratecard.RateCardDetailRepository;
import us.redshift.timesheet.service.ratecard.IRateCardDetailService;

@Service
public class RateCardDetailService implements IRateCardDetailService {

    private final RateCardDetailRepository rateCardDetailRepository;

    public RateCardDetailService(RateCardDetailRepository rateCardDetailRepository) {
        this.rateCardDetailRepository = rateCardDetailRepository;
    }

//    @Override
//    public BigDecimal getValue(Long rateCardId, Long locationId, Long skillId, Long designationId) {
//        return rateCardDetailRepository.findByRatecardIdAndLocationIdAndSkillIdAndDesignationId
//                (rateCardId, locationId, skillId, designationId).getValue();
//    }

    /*private final RateCardDetailRepository rateCardDetailRepository;

    public RateCardDetailService(RateCardDetailRepository rateCardDetailRepository) {
        this.rateCardDetailRepository = rateCardDetailRepository;
    }

    @Override
    public RateCardDetail saveRateCardDetails(RateCardDetail rateCardDetails) {
        return rateCardDetailRepository.save(rateCardDetails);
    }

    @Override
    public RateCardDetail updateRateCardDetail(RateCardDetail rateCardDetail) {
        rateCardDetailRepository.findById(rateCardDetail.getId()).orElseThrow(() -> new ResourceNotFoundException("RateCardDetail", "Id", rateCardDetail.getId()));
        return rateCardDetailRepository.save(rateCardDetail);
    }

    @Override
    public List<RateCardDetail> getAllRateCardDetail() {
        List<RateCardDetail> cardDetails = new ArrayList<>();
        rateCardDetailRepository.findAll().iterator().forEachRemaining(cardDetails::add);
        return cardDetails;
    }

    @Override
    public RateCardDetail getRateCardDetail(Long id) {
        return rateCardDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RateCardDetail", "Id", id));
    }*/
}
