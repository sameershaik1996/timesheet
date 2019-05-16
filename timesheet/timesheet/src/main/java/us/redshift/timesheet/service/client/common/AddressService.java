package us.redshift.timesheet.service.common;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.common.State;
import us.redshift.timesheet.reposistory.common.CountryRepository;
import us.redshift.timesheet.reposistory.common.StateRepository;

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
