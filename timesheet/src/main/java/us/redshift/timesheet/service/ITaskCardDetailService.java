package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.TaskCardDetail;

import java.util.List;

public interface ITaskCardDetailService {

    TaskCardDetail saveTaskCardDetail(TaskCardDetail taskCardDetail);

    TaskCardDetail updateTaskCardDetail(TaskCardDetail taskCardDetail);

    List<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String orderBy, String... fields);

    TaskCardDetail getTaskCardDetail(Long id);

    void deleteTaskCardDetailById(Long id);
}
