package us.redshift.timesheet.service.taskcard;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.reposistory.taskcard.TaskCardRepository;
import us.redshift.timesheet.reposistory.timesheet.TimeSheetRepository;
import us.redshift.timesheet.service.task.TaskService;
import us.redshift.timesheet.util.Reusable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskCardDetailService implements ITaskCardDetailService {


    private final TaskCardDetailRepository taskCardDetailRepository;
    private final TaskCardRepository taskCardRepository;
    private final TimeSheetRepository timeSheetRepository;
    private final TaskService taskService;
    private final TaskCardService taskCardService;


    public TaskCardDetailService(TaskCardDetailRepository taskCardDetailRepository, TaskCardRepository taskCardRepository, TimeSheetRepository timeSheetRepository, TaskService taskService, TaskCardService taskCardService) {
        this.taskCardDetailRepository = taskCardDetailRepository;
        this.taskCardRepository = taskCardRepository;
        this.timeSheetRepository = timeSheetRepository;
        this.taskService = taskService;
        this.taskCardService = taskCardService;
    }

    @Override
    public TaskCardDetail saveTaskCardDetail(TaskCardDetail taskCardDetail) {
        return taskCardDetailRepository.save(taskCardDetail);
    }

    @Override
    public List<TaskCardDetail> updateTaskCardDetail(List<TaskCardDetail> taskCardDetails, TimeSheetStatus status) {

        List<TaskCardDetail> taskCardDetailList = new ArrayList<>();

        taskCardDetails.forEach(taskCardDetail -> {
            if (!taskCardDetailRepository.existsById(taskCardDetail.getId()))
                throw new ResourceNotFoundException("TaskCardDetail", "ID", taskCardDetail.getId());
//            taskCardDetail.setStatus(status);
            taskCardDetailRepository.setStatusForTaskCardDetailById(status.name(), taskCardDetail.getId());
//            taskCardDetailList.add(SavedTaskCardDetail);
        });
        taskCardDetails.forEach(taskCardDetail -> {
            TaskCard taskCard = taskCardRepository.findById(taskCardDetail.getTaskCard().getId()).orElseThrow(() -> new ResourceNotFoundException("TaskCard", "ID", taskCardDetail.getTaskCard().getId()));
            TimeSheet timeSheet = timeSheetRepository.findById(taskCard.getTimeSheet().getId()).get();
            if (!status.equals(TimeSheetStatus.REJECTED)) {
                List<TaskCardDetail> cardDetailList = taskCardDetailRepository.findTaskCardDetailsByTaskCard_Id(taskCardDetail.getTaskCard().getId());
                int taskDetailApprove = 0, taskDetailReject = 0, size = cardDetailList.size();
                for (TaskCardDetail taskCardDetail1 : cardDetailList) {
                    if (taskCardDetail1.getStatus().equals(TimeSheetStatus.APPROVED))
                        taskDetailApprove++;
                    else if (taskCardDetail1.getStatus().equals(TimeSheetStatus.REJECTED))
                        taskDetailReject++;
                }

                System.out.println(taskDetailApprove + " taskCardDetail " + taskDetailReject + " Size " + size);
//                Set status to taskCard
                if (taskDetailApprove == size) {
//                update used hours to task
                    taskCard.setStatus(TimeSheetStatus.APPROVED);
                    if (taskCard.getType().equals(TaskType.BILLABLE)) {
                        taskCard = taskCardService.calculateAmount(taskCard);
                        taskService.updateTaskHours(taskCard.getTask().getId(), taskCard.getHours());
                    }
                    taskCardRepository.save(taskCard);
//                timeSheet status update
                    taskCardService.TimeSheetStatus(timeSheet);
                } else if (taskDetailReject > 0) {
                    taskCardRepository.setStatusForTaskCard(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                    timeSheetRepository.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
                }
            } else {
                taskCardRepository.setStatusForTaskCard(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                timeSheetRepository.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
            }
        });
        return taskCardDetailList;
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
    public Set<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String
            orderBy, String... fields) {
        if (!taskCardRepository.existsById(taskCardId))
            throw new ResourceNotFoundException("TaskCard", "ID", taskCardId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<TaskCardDetail> taskCardDetailList = taskCardDetailRepository.findTaskCardDetailsByTaskCard_Id(taskCardId, pageable).getContent();
        Set<TaskCardDetail> taskCardDetailSet = taskCardDetailList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskCardDetailSet;
    }


}
