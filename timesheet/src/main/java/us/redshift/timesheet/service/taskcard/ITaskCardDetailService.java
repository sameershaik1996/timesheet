package us.redshift.timesheet.service.taskcard;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.util.Date;
import java.util.List;

public interface ITaskCardDetailService {


    List<TaskCardDetail> updateTaskCardDetail(List<TaskCardDetail> taskCardDetails, TimeSheetStatus status);

    Page<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String orderBy, String... fields);

    TaskCardDetail getTaskCardDetail(Long id);

    Page<TaskCardDetail> getAllTaskCardDetails(int page, int limits, String orderBy, String... fields);

    Page<TaskCardDetail> getTaskCardDetailsByTimeSheetId_ProjectId_statusNotLike(Long timeSheetId, Long projectId, TimeSheetStatus status, int page, int limits, String orderBy, String... fields);

    void deleteTaskCardDetailById(Long id);

    int setStatusForTaskCardDetail(String toString, List<Long> taskCardDetailsId);

    Integer setStatusForTaskCardDetailByTaskCardId(String status, Long taskCardId);

    List<TaskCardDetail> getTaskCardDetailBetweenDateAndByProject(List<Long> projectId, Date fromDate, Date toDate);

    Boolean existsByIdAndStatus(Long id, TimeSheetStatus status);

}
