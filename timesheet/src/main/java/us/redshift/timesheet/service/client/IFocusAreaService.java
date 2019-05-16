package us.redshift.timesheet.service.client;

import us.redshift.timesheet.domain.client.FocusArea;

import java.util.Set;

public interface IFocusAreaService {
    FocusArea saveFocusArea(FocusArea focusArea);

    FocusArea updateFocusArea(FocusArea focusArea);

    Set<FocusArea> getAllFocusAreas();

    FocusArea getFocusAreaById(Long id);

}
