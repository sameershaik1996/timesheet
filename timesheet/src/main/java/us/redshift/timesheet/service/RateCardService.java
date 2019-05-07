package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.RateCard;
import us.redshift.timesheet.domain.RateCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.RateCardRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RateCardService implements IRateCardService {

    private final RateCardRepository rateCardRepository;

    public RateCardService(RateCardRepository rateCardRepository) {
        this.rateCardRepository = rateCardRepository;
    }


    @Override
    public RateCard saveRateCard(RateCard rateCard) {
        Set<RateCardDetail> rateCardDetails = new HashSet<>(rateCard.getRateCardDetails());
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
        List<RateCard> rateCards = new ArrayList<>();
        rateCardRepository.findAll().iterator().forEachRemaining(rateCards::add);
        return rateCards;
    }

    @Override
    public RateCard getRateCard(Long id) {
        return rateCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RateCard", "Id", id));
    }


}
