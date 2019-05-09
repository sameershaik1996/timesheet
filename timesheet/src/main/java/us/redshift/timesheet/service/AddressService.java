package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.State;
import us.redshift.timesheet.reposistory.CountryRepository;
import us.redshift.timesheet.reposistory.StateRepository;

import java.util.Set;

@Service
public class AddressService implements IAddressService {


    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;

    public AddressService(CountryRepository countryRepository, StateRepository stateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }


    @Override
    public Set<State> getStatesByCountryId(Long countryId) {
        return stateRepository.findByCountry(countryRepository.findById(countryId).get());
    }
}
