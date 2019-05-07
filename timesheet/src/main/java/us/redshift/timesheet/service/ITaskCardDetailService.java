package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.TaskCardDetail;

import java.util.List;

public interface ITaskCardDetailService {

    TaskCardDetail saveTaskCardDetail(TaskCardDetail taskCardDetail);

    TaskCardDetail updateTaskCardDetail(TaskCardDetail taskCardDetail);

    List<TaskCardDetail> getAllTaskCardDetail();

    TaskCardDetail getTaskCardDetail(Long id);
}
