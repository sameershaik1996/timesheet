package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.RateCard;

import java.util.List;

public interface IRateCardService {

    RateCard saveRateCard(RateCard rateCard);

    RateCard updateRateCard(RateCard rateCard);

    List<RateCard> getAllRateCard();

    RateCard getRateCard(Long id);
}
