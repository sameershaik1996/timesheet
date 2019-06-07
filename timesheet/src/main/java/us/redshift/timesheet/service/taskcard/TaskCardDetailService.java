package us.redshift.timesheet.service.taskcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.service.task.ITaskService;
import us.redshift.timesheet.service.timesheet.ITimeSheetService;
import us.redshift.timesheet.util.Reusable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskCardDetailService implements ITaskCardDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCardDetailService.class);

    private final TaskCardDetailRepository taskCardDetailRepository;
    private final ITimeSheetService timeSheetService;
    private final ITaskService taskService;
    private final ITaskCardService taskCardService;


    public TaskCardDetailService(TaskCardDetailRepository taskCardDetailRepository,
                                 @Lazy ITimeSheetService timeSheetService,
                                 @Lazy ITaskService taskService,
                                 @Lazy ITaskCardService taskCardService) {
        this.taskCardDetailRepository = taskCardDetailRepository;
        this.timeSheetService = timeSheetService;
        this.taskService = taskService;
        this.taskCardService = taskCardService;
    }


    @Override
    public List<TaskCardDetail> updateTaskCardDetail(List<TaskCardDetail> taskCardDetails, TimeSheetStatus status) {

        List<TaskCardDetail> taskCardDetailList = new ArrayList<>();
        taskCardDetails.forEach(taskCardDetail -> {
            if (!taskCardDetailRepository.existsById(taskCardDetail.getId()))
                throw new ResourceNotFoundException("TaskCardDetail", "ID", taskCardDetail.getId());
            if (taskCardDetailRepository.existsByIdAndStatus(taskCardDetail.getId(), taskCardDetail.getStatus()))
                throw new ValidationException("It's Already in Approved Status " + taskCardDetail.getId());
            LOGGER.info("UpdateTaskCardDetails  status Update {}", taskCardDetail.getId());
            TaskCardDetail taskCardDetail1 = taskCardDetailRepository.findById(taskCardDetail.getId()).get();
            taskCardDetail.set_index(taskCardDetail1.get_index());
            System.out.println(taskCardDetail1.get_index() + "index");

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
                TaskCard taskCard = taskCardService.getTaskCardById(taskCardDetail.getTaskCard().getId());
                TimeSheet timeSheet = timeSheetService.getTimeSheet(taskCard.getTimeSheet().getId());
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
                        taskCardService.saveTaskCard(taskCard);
//                timeSheet status update
                        taskCardService.TimeSheetStatus(timeSheet);
                    } else if (taskDetailReject > 0) {
                        LOGGER.info("UpdateTaskCardDetails status Updated as Rejected {}", taskCard.getId());
                        taskCardService.setStatusForTaskCard(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                        timeSheetService.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
                    }
                } else {
                    LOGGER.info("UpdateTaskCardDetails status Updated as Rejected {}", taskCard.getId());
                    taskCardService.setStatusForTaskCard(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                    timeSheetService.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
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
    public Page<TaskCardDetail> getAllTaskCardDetails(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskCardDetailRepository.findAll(pageable);
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
        return taskCardDetailRepository.setStatusForTaskCardDetailByTaskCardId(TimeSheetStatus.INVOICE_RAISED.toString(), taskCardDetailsId);
    }

    @Override
    public Integer setStatusForTaskCardDetailByTaskCardId(String status, Long taskCardId) {
        return taskCardDetailRepository.setStatusForTaskCardDetailByTaskCardId(status, taskCardId);
    }

    @Override
    public List<TaskCardDetail> getTaskCardDetailBetweenDateAndByProject(List<Long> projectId, Date fromDate, Date toDate) {
        return taskCardDetailRepository.findTaskCardDetailsByTaskCard_Project_IdInAndDateBetweenAndStatusAndTaskCard_Type(projectId, fromDate, toDate, TimeSheetStatus.INVOICE_RAISED, TaskType.BILLABLE);
    }

    @Override
    public Boolean existsByIdAndStatus(Long id, TimeSheetStatus status) {
        return taskCardDetailRepository.existsByIdAndStatus(id, status);
    }

    @Override
    public Page<TaskCardDetail> getTaskCardTaskCardDetailsByPagination(Long taskCardId, int page, int limits, String
            orderBy, String... fields) {
        if (!taskCardService.existsById(taskCardId))
            throw new ResourceNotFoundException("TaskCard", "ID", taskCardId);
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskCardDetailRepository.findTaskCardDetailsByTaskCard_Id(taskCardId, pageable);
    }


}
