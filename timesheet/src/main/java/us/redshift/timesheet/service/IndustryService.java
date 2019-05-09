package us.redshift.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.Industry;
import us.redshift.timesheet.reposistory.IndustryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class IndustryService implements IIndustryService {

    @Autowired
    private IndustryRepository industryRepository;

    @Override
    public Industry saveIndustry(Industry industry) {
        return industryRepository.save(industry);
    }

    @Override
    public Industry updateIndustry(Industry industry) {
        return industryRepository.save(industry);
    }

    @Override
    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }

    @Override
    public Industry getIndustryById(Long id) {
        return industryRepository.findById(id).get();
    }

}
