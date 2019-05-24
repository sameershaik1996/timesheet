package us.redshift.timesheet.service.ratecard;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.project.ProjectType;
import us.redshift.timesheet.domain.ratecard.RateCard;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.ratecard.RateCardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RateCardService implements IRateCardService {

    private final RateCardRepository rateCardRepository;

    public RateCardService(RateCardRepository rateCardRepository) {
        this.rateCardRepository = rateCardRepository;
    }


    @Override
    public RateCard saveRateCard(RateCard rateCard) {
        List<RateCardDetail> rateCardDetails = new ArrayList<>(rateCard.getRateCardDetails());
//        System.out.println(rateCardDetails.size());
        rateCardDetails.forEach(cardDetail -> {
            rateCard.addRateCardDetail(cardDetail);
        });
        return rateCardRepository.save(rateCard);
    }

    @Override
    public RateCard updateRateCard(RateCard rateCard) {
        rateCardRepository.findById(rateCard.getId()).orElseThrow(() -> new ResourceNotFoundException("RateCard", "Id", rateCard.getId()));
        List<RateCardDetail> cardDetails = new ArrayList<>(rateCard.getRateCardDetails());
        cardDetails.forEach(cardDetail -> rateCard.addRateCardDetail(cardDetail));
        return rateCardRepository.save(rateCard);
    }

    @Override
    public List<RateCard> getAllRateCard() {
        List<RateCard> rateCardList = rateCardRepository.findAll();
        return rateCardList;
    }

    @Override
    public RateCard getRateCardByProjectTypeAndIsDefault(ProjectType type, Boolean isDefault) {
        return rateCardRepository.findByProjectTypeAndIsDefault(type, isDefault);
    }

    @Override
    public RateCard getRateCard(Long id) {
        return rateCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RateCard", "Id", id));
    }


}
