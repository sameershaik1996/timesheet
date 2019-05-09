package us.redshift.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.timesheet.domain.FocusArea;
import us.redshift.timesheet.reposistory.FocusAreaRepository;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<FocusArea> getAllFocusAreas() {
        return focusAreaRepository.findAll();
    }

    @Override
    public FocusArea getFocusAreaById(Long id) {
        return focusAreaRepository.findById(id).get();
    }

}
