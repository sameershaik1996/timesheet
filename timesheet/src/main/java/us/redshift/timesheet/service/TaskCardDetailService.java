package us.redshift.timesheet.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.TaskCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.TaskCardDetailRepository;
import us.redshift.timesheet.reposistory.TaskCardRepository;
import us.redshift.timesheet.util.Reusable;

import java.util.List;

@Service
public class TaskCardDetailService implements ITaskCardDetailService {


    private final TaskCardDetailRepository taskCardDetailRepository;
    private final TaskCardRepository taskCardRepository;

    public TaskCardDetailService(TaskCardDetailRepository taskCardDetailRepository, TaskCardRepository taskCardRepository) {
        this.taskCardDetailRepository = taskCardDetailRepository;
        this.taskCardRepository = taskCardRepository;
    }

    @Override
    public TaskCardDetail saveTaskCardDetail(TaskCardDetail taskCardDetail) {
        return taskCardDetailRepository.save(taskCardDetail);
    }

    @Override
    public TaskCardDetail updateTaskCardDetail(TaskCardDetail taskCardDetail) {
        if (!taskCardDetailRepository.existsById(taskCardDetail.getId()))
            throw new ResourceNotFoundException("TaskCardDetail", "ID", taskCardDetail.getId());
        return taskCardDetailRepository.save(taskCardDetail);
    }

    @Override
    public TaskCardDetail getTaskCardDetail(Long id) {
        return taskCardDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TaskCardDetail", "ID", id));
    }

    @Override
    public void deleteTaskCardDetailById(Long id) {

        if (!taskCardDetailRepository.existsById(id))
            throw new ResourceNotFoundException("TaskCardDetail", "ID", id);

        taskCardDetailRepository.deleteById(id);
    }

    @Override
    public List<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String orderBy, String... fields) {
        if (!taskCardRepository.existsById(taskCardId))
            throw new ResourceNotFoundException("TaskCard", "ID", taskCardId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskCardDetailRepository.findTaskCardDetailsByTaskCard_Id(taskCardId, pageable).getContent();
    }


}
