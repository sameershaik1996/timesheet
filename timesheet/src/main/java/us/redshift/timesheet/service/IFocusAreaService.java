package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.FocusArea;

import java.util.List;

public interface IFocusAreaService {
    FocusArea saveFocusArea(FocusArea focusArea);

    FocusArea updateFocusArea(FocusArea focusArea);

    List<FocusArea> getAllFocusAreas();

    FocusArea getFocusAreaById(Long id);

}
