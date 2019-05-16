package us.redshift.timesheet.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.client.FocusArea;
import us.redshift.timesheet.reposistory.client.FocusAreaRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class FocusAreaService implements IFocusAreaService {

    @Autowired
    private FocusAreaRepository focusAreaRepository;

    @Override
    public FocusArea saveFocusArea(FocusArea focusArea) {
        return focusAreaRepository.save(focusArea);
    }

    @Override
    public FocusArea updateFocusArea(FocusArea focusArea) {
        return focusAreaRepository.save(focusArea);
    }

    @Override
    public Set<FocusArea> getAllFocusAreas() {
        List<FocusArea> focusAreaList = focusAreaRepository.findAll();
        Set<FocusArea> focusAreaSet = focusAreaList.stream().collect(Collectors.toCollection(HashSet::new));
        return focusAreaSet;
    }

    @Override
    public FocusArea getFocusAreaById(Long id) {
        return focusAreaRepository.findById(id).get();
    }

}
