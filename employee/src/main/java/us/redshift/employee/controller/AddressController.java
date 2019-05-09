package us.redshift.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.redshift.employee.service.IAddressService;

import javax.xml.ws.Response;

@RestController
@RequestMapping("employee/v1/api/address")
public class AddressController {

    @Autowired
    IAddressService addressService;

    @GetMapping("country")
    public ResponseEntity<?> getAllCountries(){
        return new ResponseEntity<>(addressService.getAllCountries(),HttpStatus.OK);
    }

    @GetMapping("state/{countryId}")
    public ResponseEntity<?> getStatesByCountryId(@PathVariable Long countryId){
        return new ResponseEntity<>(addressService.getStatesByCountryId(countryId), HttpStatus.OK);
    }



}
