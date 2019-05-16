package us.redshift.timesheet.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.redshift.timesheet.service.common.IAddressService;

@RestController
@RequestMapping("timesheet/v1/api/address")
public class AddressController {


    private final IAddressService addressService;

    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping("/state/{countryId}")
    public ResponseEntity<?> getStatesByCountryId(@PathVariable Long countryId) {
        return new ResponseEntity<>(addressService.getStatesByCountryId(countryId), HttpStatus.OK);
    }


}
