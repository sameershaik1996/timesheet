package us.redshift.timesheet.service.ratecard;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.ratecard.RateCard;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.ratecard.RateCardRepository;
import us.redshift.timesheet.service.ratecard.IRateCardService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<RateCard> getAllRateCard() {
        List<RateCard> rateCardList = rateCardRepository.findAll();
        Set<RateCard> rateCardSet = rateCardList.stream().collect(Collectors.toCollection(HashSet::new));
        return rateCardSet;
    }

    @Override
    public RateCard getRateCard(Long id) {
        return rateCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RateCard", "Id", id));
    }


}
