package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.TaskCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.TaskCardDetailRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskCardDetailService implements ITaskCardDetailService {


    private final TaskCardDetailRepository taskCardDetailRepository;

    public TaskCardDetailService(TaskCardDetailRepository taskCardDetailRepository) {
        this.taskCardDetailRepository = taskCardDetailRepository;
    }

    @Override
    public TaskCardDetail saveTaskCardDetail(TaskCardDetail taskCardDetail) {
        return taskCardDetailRepository.save(taskCardDetail);
    }

    @Override
    public TaskCardDetail updateTaskCardDetail(TaskCardDetail taskCardDetail) {
        taskCardDetailRepository.findById(taskCardDetail.getId()).orElseThrow(() -> new ResourceNotFoundException("TaskCardDetail", "ID", taskCardDetail.getId()));
        return taskCardDetailRepository.save(taskCardDetail);
    }

    @Override
    public List<TaskCardDetail> getAllTaskCardDetail() {
        List<TaskCardDetail> taskCardDetails = new ArrayList<>();
        taskCardDetailRepository.findAll().iterator().forEachRemaining(taskCardDetails::add);
        return taskCardDetails;
    }

    @Override
    public TaskCardDetail getTaskCardDetail(Long id) {
        return taskCardDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TaskCardDetail", "ID", id));
    }
}
