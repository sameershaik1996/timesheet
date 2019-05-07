package us.redshift.timesheet.controller;

import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.RateCard;
import us.redshift.timesheet.service.IRateCardService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("timesheet/v1/api/")
public class RateCardController {

    private final IRateCardService rateCardService;

    public RateCardController(IRateCardService rateCardService) {
        this.rateCardService = rateCardService;
    }

    @PostMapping("ratecard/save")
    public RateCard saveRateCard(@Valid @RequestBody RateCard rateCard) {
        return rateCardService.saveRateCard(rateCard);
    }

    @PutMapping("ratecard/update")
    public RateCard updateRateCard(@Valid @RequestBody RateCard rateCard) {
        return rateCardService.updateRateCard(rateCard);
    }

    @GetMapping({"ratecard/"})
    public List<RateCard> getAllRateCard() {
        return rateCardService.getAllRateCard();
    }

    @GetMapping("ratecard/{id}")
    public RateCard getRateCard(@PathVariable(value = "id") Long id) {
        return rateCardService.getRateCard(id);
    }

}
