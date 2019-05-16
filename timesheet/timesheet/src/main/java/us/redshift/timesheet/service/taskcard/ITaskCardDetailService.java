package us.redshift.timesheet.service.taskcard;

import us.redshift.timesheet.domain.taskcard.TaskCardDetail;

import java.util.Set;

public interface ITaskCardDetailService {

    TaskCardDetail saveTaskCardDetail(TaskCardDetail taskCardDetail);

    TaskCardDetail updateTaskCardDetail(TaskCardDetail taskCardDetail);

    Set<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String orderBy, String... fields);

    TaskCardDetail getTaskCardDetail(Long id);

    void deleteTaskCardDetailById(Long id);
}
