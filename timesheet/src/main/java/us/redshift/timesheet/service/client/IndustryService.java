package us.redshift.timesheet.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.client.Industry;
import us.redshift.timesheet.reposistory.client.IndustryRepository;

import java.util.List;

@Service
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
        return industryRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Industry getIndustryById(Long id) {
        return industryRepository.findById(id).get();
    }

}
