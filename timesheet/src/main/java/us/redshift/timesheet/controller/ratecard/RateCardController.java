package us.redshift.timesheet.controller.ratecard;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.project.ProjectType;
import us.redshift.timesheet.domain.ratecard.RateCard;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.dto.ratecard.RateCardDetailDto;
import us.redshift.timesheet.reposistory.ratecard.RateCardDetailRepository;
import us.redshift.timesheet.service.ratecard.IRateCardService;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        return new ResponseEntity<>(rateCardService.saveRateCard(rateCard), HttpStatus.CREATED);
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

        Set<RateCardDetail> rateCardDetails = rateCardDetailRepository.findAllByRateCard_ProjectTypeAndRateCard_IsDefaultOrderByLocation(ProjectType.FIXED_BID, true);

        Type targetSetType = new TypeToken<Set<RateCardDetailDto>>() {
        }.getType();


        this.mapper.addMappings(new PropertyMap<RateCardDetail, RateCardDetailDto>() {
            protected void configure() {
                map().setLocationId(source.getLocation().getId());
            }
        });

        Set<RateCardDetailDto> detailDtos = mapper.map(rateCardDetails, targetSetType);

        Map<Long, List<RateCardDetailDto>> groupByRateCard = detailDtos.stream().collect(Collectors.groupingBy(RateCardDetailDto::getLocationId));

        return new ResponseEntity<>(groupByRateCard, HttpStatus.OK);
    }


}
