package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Industry;

import java.util.List;

public interface IIndustryService {
    Industry saveIndustry(Industry industry);

    Industry updateIndustry(Industry industry);

    List<Industry> getAllIndustries();

    Industry getIndustryById(Long id);

}
