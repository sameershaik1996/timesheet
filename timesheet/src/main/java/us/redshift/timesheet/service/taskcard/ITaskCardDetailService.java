package us.redshift.timesheet.service.taskcard;

import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.List;
import java.util.Set;

public interface ITaskCardDetailService {

    TaskCardDetail saveTaskCardDetail(TaskCardDetail taskCardDetail);

    List<TaskCardDetail> updateTaskCardDetail(List<TaskCardDetail> taskCardDetails, TimeSheetStatus status);

    Set<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String orderBy, String... fields);

    TaskCardDetail getTaskCardDetail(Long id);

    void deleteTaskCardDetailById(Long id);
}
