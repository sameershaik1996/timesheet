package us.redshift.timesheet.service.taskcard;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.List;

public interface ITaskCardDetailService {


    List<TaskCardDetail> updateTaskCardDetail(List<TaskCardDetail> taskCardDetails, TimeSheetStatus status);

    Page<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String orderBy, String... fields);

    TaskCardDetail getTaskCardDetail(Long id);

    Page<TaskCardDetail> getTaskCardDetailsByTimeSheetId_ProjectId_statusNotLike(Long timeSheetId, Long projectId, TimeSheetStatus status, int page, int limits, String orderBy, String... fields);

    void deleteTaskCardDetailById(Long id);

    int setStatusForTaskCardDetail(String toString, List<Long> taskCardDetailsId);
}
