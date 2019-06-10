package us.redshift.timesheet.controller.ratecard;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.ratecard.RateCard;
import us.redshift.timesheet.reposistory.ratecard.RateCardDetailRepository;
import us.redshift.timesheet.service.ratecard.IRateCardService;

import javax.validation.Valid;

@RestController
@RequestMapping("timesheet/v1/api/")
public class RateCardController {

    private final IRateCardService rateCardService;

    private final ModelMapper mapper;

    private final RateCardDetailRepository rateCardDetailRepository;

    public RateCardController(IRateCardService rateCardService, ModelMapper mapper, RateCardDetailRepository rateCardDetailRepository) {
        this.rateCardService = rateCardService;
        this.mapper = mapper;
        this.rateCardDetailRepository = rateCardDetailRepository;
    }

    @PostMapping("ratecard/save")
    public ResponseEntity<?> saveRateCard(@Valid @RequestBody RateCard rateCard) {
        return new ResponseEntity<>(rateCardService.saveRateCard(rateCard), HttpStatus.CREATED);
    }

    @PutMapping("ratecard/update")
    public ResponseEntity<?> updateRateCard(@Valid @RequestBody RateCard rateCard) {
        return new ResponseEntity<>(rateCardService.updateRateCard(rateCard), HttpStatus.OK);
    }

    @GetMapping({"ratecard/get"})
    public ResponseEntity<?> getAllRateCard(@RequestParam(value = "isDefault", defaultValue = "false", required = false) Boolean isDefault,
                                            @RequestParam(value = "id", required = false) Long id) {
        if (isDefault != false) {
            RateCard rateCard = rateCardService.getRateCardByIsDefault(true);
            if (rateCard == null) {
                RateCard newRateCard = new RateCard();
                newRateCard.setIsDefault(true);
                rateCard = rateCardService.saveRateCard(newRateCard);

            }
            return new ResponseEntity<>(rateCard, HttpStatus.OK);
        } else if (id != null)
            return new ResponseEntity<>(rateCardService.getRateCard(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(rateCardService.getAllRateCard(), HttpStatus.OK);
    }


    /*@GetMapping("ratecard/group")
    public ResponseEntity<?> getRateCardGroupBy() {

        List<RateCardDetail> rateCardDetails = rateCardDetailRepository.findAllByRateCard_ProjectTypeAndRateCard_IsDefaultOrderByLocation(ProjectType.FIXED_BID, true);

        Type targetSetType = new TypeToken<List<RateCardDetailDto>>() {
        }.getType();


//

        List<RateCardDetailDto> detailDtos = mapper.map(rateCardDetails, targetSetType);

        Map<Long, List<RateCardDetailDto>> groupByRateCard = detailDtos.stream().collect(Collectors.groupingBy(RateCardDetailDto::getLocationId));

        return new ResponseEntity<>(groupByRateCard, HttpStatus.OK);
    }*/


}
