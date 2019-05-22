package us.redshift.timesheet.service.ratecard;

import us.redshift.timesheet.domain.project.ProjectType;
import us.redshift.timesheet.domain.ratecard.RateCard;

import java.util.List;

public interface IRateCardService {

    RateCard saveRateCard(RateCard rateCard);

    RateCard updateRateCard(RateCard rateCard);

    List<RateCard> getAllRateCard();

    RateCard getRateCardByProjectTypeAndIsDefault(ProjectType type, Boolean isDefault);


    RateCard getRateCard(Long id);


}
