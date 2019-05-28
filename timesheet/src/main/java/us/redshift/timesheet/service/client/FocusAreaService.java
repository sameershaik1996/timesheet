package us.redshift.timesheet.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.client.FocusArea;
import us.redshift.timesheet.reposistory.client.FocusAreaRepository;

import java.util.List;

@Service
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
        return focusAreaRepository.findAllByOrderByCodeAsc();
    }

    @Override
    public FocusArea getFocusAreaById(Long id) {
        return focusAreaRepository.findById(id).get();
    }

}
