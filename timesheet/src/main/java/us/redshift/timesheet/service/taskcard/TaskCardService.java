package us.redshift.timesheet.service.taskcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.ratecard.RateCardDetail;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.exception.ValidationException;
import us.redshift.timesheet.reposistory.taskcard.TaskCardRepository;
import us.redshift.timesheet.service.project.IProjectService;
import us.redshift.timesheet.service.ratecard.IRateCardDetailService;
import us.redshift.timesheet.service.task.ITaskService;
import us.redshift.timesheet.service.timesheet.ITimeSheetService;
import us.redshift.timesheet.util.Reusable;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskCardService implements ITaskCardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCardService.class);

    private final TaskCardRepository taskCardRepository;
    private final IRateCardDetailService rateCardDetailService;
    private final IProjectService projectService;
    private final ITaskCardDetailService taskCardDetailService;
    private final ITaskService taskService;
    private final ITimeSheetService timeSheetService;

    public TaskCardService(TaskCardRepository taskCardRepository,
                           @Lazy IRateCardDetailService rateCardDetailService,
                           @Lazy IProjectService projectService,
                           @Lazy ITaskCardDetailService taskCardDetailService,
                           @Lazy ITaskService taskService,
                           @Lazy ITimeSheetService timeSheetService) {
        this.taskCardRepository = taskCardRepository;
        this.rateCardDetailService = rateCardDetailService;
        this.projectService = projectService;
        this.taskCardDetailService = taskCardDetailService;
        this.taskService = taskService;
        this.timeSheetService = timeSheetService;
    }


    @Override
    public List<TaskCard> updateTaskCard(List<TaskCard> taskCards, TimeSheetStatus status) {
        List<TaskCard> taskCardList = new ArrayList<>();

        taskCards.forEach(taskCard -> {
            if (!taskCardRepository.existsById(taskCard.getId()))
                throw new ResourceNotFoundException("TaskCard", "ID", taskCard.getId());
            taskCard.setStatus(status);
            TimeSheet timeSheet = timeSheetService.getTimeSheet(taskCard.getTimeSheet().getId());
//          calculateAmount()
            if (status.equals(TimeSheetStatus.APPROVED)) {
                taskCard = addTaskCardDetails(taskCard);
                taskCard = calculateAmount(taskCard);
            }
            TaskCard savedTaskCard = taskCardRepository.save(taskCard);
            taskCardList.add(savedTaskCard);
            if (status.equals(TimeSheetStatus.APPROVED)) {
                taskCardDetailService.setStatusForTaskCardDetailByTaskCardId(TimeSheetStatus.APPROVED.name(), taskCard.getId());
                if (taskCard.getType().equals(TaskType.BILLABLE)) {
//                    Update usedHour
                    LOGGER.info(" UpdateTaskCard {} updateTask Hour With ", taskCard.getHours());
                    taskService.updateTaskHours(taskCard.getTask().getId(), taskCard.getHours());
                }
//                timeSheet status update
                TimeSheetStatus(timeSheet);
            } else {
                taskCardDetailService.setStatusForTaskCardDetailByTaskCardId(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                timeSheetService.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
            }
        });
//        Set<TaskCard> cardSet = taskCardList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskCardList;
    }

    @Override
    public TaskCard saveTaskCard(TaskCard taskCard) {
        return taskCardRepository.save(taskCard);
    }

    @Override
    public Page<TaskCard> getAllTaskCardByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
//        List<TaskCard> taskCardList = taskCardRepository.findAll(pageable).getContent();
//        Set<TaskCard> taskCardSet = taskCardList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskCardRepository.findAll(pageable);

    }

    @Override
    public void deleteTaskCardById(Long id) {
        if (!taskCardRepository.existsById(id))
            throw new ResourceNotFoundException("TaskCard", "ID", id);
        taskCardRepository.deleteById(id);
        LOGGER.info(" Deleted TaskCard id {}", id);
    }

    @Override
    public List<TaskCard> getAllTaskCardByMangerId(Long managerId) {
        List<Project> projectList = projectService.getAllProjectByManagerId(managerId);
        return taskCardRepository.findByStatusNotLikeAndProjectIn(TimeSheetStatus.PENDING, projectList);
    }

    @Override
    public List<TaskCard> getTaskCardByStatusAndType(TimeSheetStatus status, TaskType type) {
        return taskCardRepository.findByStatusAndType(status, type);
    }

    public List<TaskCard> getAllTaskCardByProject(Long projectId) {
        return taskCardRepository.findByStatusNotLikeAndProject_IdOrderByEmployeeIdAscTaskCardDetailsAsc(TimeSheetStatus.PENDING, projectId);
    }

    @Override
    public Integer setStatusForTaskCard(String status, Long taskCardId) {
        return taskCardRepository.setStatusForTaskCard(status, taskCardId);
    }

    @Override
    public Boolean existsById(Long taskCardId) {
        return taskCardRepository.existsById(taskCardId);
    }

    @Override
    public TaskCard getTaskCardById(Long id) {
        return taskCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TaskCard", "ID", id));
    }

    @Override
    public TaskCard calculateAmount(TaskCard card) {

//      Get rateCardId
        Task task = taskService.getTaskById(card
                .getTask().getId());
        Long rateCardId = null;
        BigDecimal ratePerHour = new BigDecimal(0);

        if (TaskType.BILLABLE.equals(task.getType())) {
            if (task.getProject().getRateCard() != null)
                rateCardId = task.getProject().getRateCard().getId();


//      Assign ratePerHour
            if (card.getLocation() != null && card.getRole() != null && rateCardId != null) {
//       TODo Add RateCard Id
                RateCardDetail rateCardDetail = rateCardDetailService.findByRateCard_IdAndLocation_IdAndEmployeeRole_Id(rateCardId, card.getLocation().getId(), card.getRole().getId());
                if (rateCardDetail != null)
                    ratePerHour = rateCardDetail.getValue();
                LOGGER.info(" UpdateTaskCard Calculate Amount RatePerHour {}", ratePerHour);

            }
            card.setRatePerHour(ratePerHour);
        }

//      sum of hours
        BigDecimal hours = new BigDecimal(0);
        List<TaskCardDetail> cardDetails = card.getTaskCardDetails();
        for (TaskCardDetail cardDetail : cardDetails) {
            hours = hours.add(cardDetail.getHours());
        }

//      SetAmount and Hour
        LOGGER.info(" UpdateTaskCard Calculate Amount Total Hours{}", hours);
        card.setHours(hours);
        card.setAmount(Reusable.multiplyRates(hours, ratePerHour));
        card.setTask(task);
        return card;
    }

    @Override
    public void UpdateActualDates(Long projectId, Long taskId) {

        Project project = projectService.getProjectById(projectId);
        Task task = taskService.getTaskById(taskId);
        if (project.getStartedOn() == null && project.getStartDate() != null) {

            ////////System.out.println("Project and Task Actual Dates");

            project.setStartedOn(new Date(System.currentTimeMillis()));
            projectService.saveProject(project);
            if (task.getStartedOn() == null && task.getStartDate() != null) {
                task.setStartedOn(new Date(System.currentTimeMillis()));
                try {
                    taskService.saveTask(task);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void checkDates(TaskCardDetail taskCardDetail, Date date) {

        java.sql.Date taskCardDetailDate = new java.sql.Date(taskCardDetail.getDate().getTime());
        java.sql.Date joiningDate = new java.sql.Date(date.getTime());
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        if (taskCardDetailDate.compareTo(joiningDate) > 1)
            throw new ValidationException("Please fill from your joining Date");
        if (currentDate.compareTo(taskCardDetailDate) < 1)
            if (!currentDate.equals(taskCardDetailDate))
                throw new ValidationException("Don't Fill for Future Dates");

    }

    @Override
    public void TimeSheetStatus(TimeSheet timeSheet) {
        int taskCardApprove = 0, taskCardReject = 0;
        int size = timeSheet.getTaskCards().size();

        for (TaskCard taskCard1 : timeSheet.getTaskCards()) {
            if (taskCard1.getStatus().equals(TimeSheetStatus.APPROVED))
                taskCardApprove++;
            else if (taskCard1.getStatus().equals(TimeSheetStatus.REJECTED))
                taskCardReject++;
        }

        if (taskCardApprove == size) {
            LOGGER.info(" TimeSheet Status -> Approved TimeSheet {} ", timeSheet.getId());
            timeSheetService.setStatusForTimeSheet(TimeSheetStatus.APPROVED.name(), timeSheet.getId());
        } else if (taskCardReject > 1) {
            LOGGER.info(" TimeSheet Status -> Rejected TimeSheet {} ", timeSheet.getId());
            timeSheetService.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
        }

    }


    public TaskCard addTaskCardDetails(TaskCard taskCard) {

        taskCard.getTaskCardDetails().stream().forEach(taskCardDetail -> taskCard.add(taskCardDetail));

        return taskCard;
    }


}

