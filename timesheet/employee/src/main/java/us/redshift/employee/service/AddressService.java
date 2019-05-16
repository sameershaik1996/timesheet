package us.redshift.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.employee.domain.common.Country;
import us.redshift.employee.domain.common.State;
import us.redshift.employee.repository.CountryRepository;
import us.redshift.employee.repository.StateRepository;

import java.util.List;
import java.util.Set;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Override
    public Set<State> getStatesByCountryId(Long countryId) {
        return stateRepository.findByCountry(countryRepository.findById(countryId).get());
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
