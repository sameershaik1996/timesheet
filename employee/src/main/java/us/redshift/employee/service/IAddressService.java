package us.redshift.employee.service;

import us.redshift.employee.domain.common.Country;
import us.redshift.employee.domain.common.State;

import java.util.List;
import java.util.Set;

public interface IAddressService {


    Set<State> getStatesByCountryId(Long countryId);

    List<Country> getAllCountries();
}
