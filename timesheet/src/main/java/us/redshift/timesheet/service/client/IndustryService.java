package us.redshift.timesheet.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.client.Industry;
import us.redshift.timesheet.reposistory.client.IndustryRepository;
import us.redshift.timesheet.service.client.IIndustryService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Industry> getAllIndustries() {
        List<Industry> industries = industryRepository.findAll();
        Set<Industry> industrySet = industries.stream().collect(Collectors.toCollection(HashSet::new));
        return industrySet;
    }

    @Override
    public Industry getIndustryById(Long id) {
        return industryRepository.findById(id).get();
    }

}
