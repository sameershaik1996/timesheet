package us.redshift.timesheet.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.RateCard;
import us.redshift.timesheet.domain.RateCardDetail;
import us.redshift.timesheet.reposistory.RateCardDetailRepository;
import us.redshift.timesheet.service.IRateCardService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        RateCard card = mapper.map(rateCard, RateCard.class);
        return new ResponseEntity<>(rateCardService.saveRateCard(card), HttpStatus.CREATED);
    }

    @PutMapping("ratecard/update")
    public ResponseEntity<?> updateRateCard(@Valid @RequestBody RateCard rateCard) {
        return new ResponseEntity<>(rateCardService.updateRateCard(rateCard), HttpStatus.OK);
    }

    @GetMapping({"ratecard"})
    public ResponseEntity<?> getAllRateCard() {
        return new ResponseEntity<>(rateCardService.getAllRateCard(), HttpStatus.OK);
    }

    @GetMapping("ratecard/{id}")
    public ResponseEntity<?> getRateCard(@PathVariable Long id) {
        return new ResponseEntity<>(rateCardService.getRateCard(id), HttpStatus.OK);
    }

    @GetMapping("ratecard/group")
    public ResponseEntity<?> getRateCardGroupBy() {

        List<RateCardDetail> rateCardDetails = rateCardDetailRepository.findAll();

        Map<Long, List<RateCardDetail>> groupByPriceMap =
                rateCardDetails.stream().collect(Collectors.groupingBy(RateCardDetail::getSkillId));

        return new ResponseEntity<>(groupByPriceMap, HttpStatus.OK);
    }


}
