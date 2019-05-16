package us.redshift.timesheet.service.common;

import us.redshift.timesheet.domain.common.State;

import java.util.Set;

public interface IAddressService {


    Set<State> getStatesByCountryId(Long countryId);
}
