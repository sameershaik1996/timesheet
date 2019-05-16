package us.redshift.timesheet.service.client;

import us.redshift.timesheet.domain.client.Industry;

import java.util.Set;

public interface IIndustryService {
    Industry saveIndustry(Industry industry);

    Industry updateIndustry(Industry industry);

    Set<Industry> getAllIndustries();

    Industry getIndustryById(Long id);

}
