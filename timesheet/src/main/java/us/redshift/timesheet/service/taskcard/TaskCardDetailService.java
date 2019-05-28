package us.redshift.timesheet.service.taskcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
import java.util.List;

@Service
public class TaskCardDetailService implements ITaskCardDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCardDetailService.class);

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
    public List<TaskCardDetail> updateTaskCardDetail(List<TaskCardDetail> taskCardDetails, TimeSheetStatus status) {

        List<TaskCardDetail> taskCardDetailList = new ArrayList<>();
        taskCardDetails.forEach(taskCardDetail -> {
            if (!taskCardDetailRepository.existsById(taskCardDetail.getId()))
                throw new ResourceNotFoundException("TaskCardDetail", "ID", taskCardDetail.getId());
            LOGGER.info("UpdateTaskCardDetails  status Update {}", taskCardDetail.getId());
            taskCardDetail.setStatus(status);
//            taskCardDetailRepository.save(taskCardDetail);
            taskCardDetailList.add(taskCardDetail);
//            taskCardDetailRepository.setStatusForTaskCardDetailById(status.name(), taskCardDetail.getId());
        });
        taskCardDetailRepository.saveAll(taskCardDetailList);
        List<Long> checkList = new ArrayList<>();
        taskCardDetails.forEach(taskCardDetail -> {
            if (!checkList.contains(taskCardDetail.getTaskCard().getId())) {
                checkList.add(taskCardDetail.getTaskCard().getId());
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

//                List status to taskCard
                    if (taskDetailApprove == size) {
//                update used hours to task
                        LOGGER.info("Size of taskCardDetails {}", taskDetailApprove);
                        taskCard.setStatus(TimeSheetStatus.APPROVED);
                        if (taskCard.getType().equals(TaskType.BILLABLE)) {
                            LOGGER.info("UpdateTaskCardDetails calculateAmount {}", taskCard.getId());
                            taskCard = taskCardService.calculateAmount(taskCard);
                            LOGGER.info("UpdateTaskCardDetails updateTask Hour With {}", taskCard.getHours());
                            taskService.updateTaskHours(taskCard.getTask().getId(), taskCard.getHours());
                        }
                        taskCardRepository.save(taskCard);
//                timeSheet status update
                        taskCardService.TimeSheetStatus(timeSheet);
                    } else if (taskDetailReject > 0) {
                        LOGGER.info("UpdateTaskCardDetails status Updated as Rejected {}", taskCard.getId());
                        taskCardRepository.setStatusForTaskCard(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                        timeSheetRepository.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
                    }
                } else {
                    LOGGER.info("UpdateTaskCardDetails status Updated as Rejected {}", taskCard.getId());
                    taskCardRepository.setStatusForTaskCard(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                    timeSheetRepository.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
                }
            }
        });
        return new ArrayList<>();
    }

    @Override
    public TaskCardDetail getTaskCardDetail(Long id) {
        return taskCardDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TaskCardDetail", "ID", id));
    }

    @Override
    public Page<TaskCardDetail> getTaskCardDetailsByTimeSheetId_ProjectId_statusNotLike(Long timeSheetId, Long projectId, TimeSheetStatus status, int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskCardDetailRepository.findAllByTaskCard_TimeSheet_IdAndTaskCard_Project_IdAndStatusNotLikeOrderByDate(timeSheetId, projectId, status, pageable);
    }


    @Override
    public void deleteTaskCardDetailById(Long id) {

        if (!taskCardDetailRepository.existsById(id))
            throw new ResourceNotFoundException("TaskCardDetail", "ID", id);

        taskCardDetailRepository.deleteById(id);
    }

    @Override
    public int setStatusForTaskCardDetail(String toString, List<Long> taskCardDetailsId) {
        return taskCardDetailRepository.setStatusForTaskCardDetail(TimeSheetStatus.INVOICE_RAISED.toString(), taskCardDetailsId);
    }

    @Override
    public Page<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String
            orderBy, String... fields) {
        if (!taskCardRepository.existsById(taskCardId))
            throw new ResourceNotFoundException("TaskCard", "ID", taskCardId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskCardDetailRepository.findTaskCardDetailsByTaskCard_Id(taskCardId, pageable);
    }


}
