package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.State;

import java.util.Set;

public interface IAddressService {


    Set<State> getStatesByCountryId(Long countryId);
}
