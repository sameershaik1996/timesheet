package us.redshift.timesheet.service.taskcard;

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
import us.redshift.timesheet.dto.common.EmployeeDto;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.feignclient.EmployeeFeign;
import us.redshift.timesheet.reposistory.project.ProjectRepository;
import us.redshift.timesheet.reposistory.ratecard.RateCardDetailRepository;
import us.redshift.timesheet.reposistory.task.TaskRepository;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.reposistory.taskcard.TaskCardRepository;
import us.redshift.timesheet.reposistory.timesheet.TimeSheetRepository;
import us.redshift.timesheet.service.task.TaskService;
import us.redshift.timesheet.util.Reusable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskCardService implements ITaskCardService {

    private final TaskCardRepository taskCardRepository;

    private final RateCardDetailRepository rateCardDetailRepository;

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final TaskCardDetailRepository taskCardDetailRepository;

    private final TaskService taskService;

    private final TimeSheetRepository timeSheetRepository;

    private final EmployeeFeign employeeFeign;


    public TaskCardService(TaskCardRepository taskCardRepository, RateCardDetailRepository rateCardDetailRepository, TaskRepository taskRepository, ProjectRepository projectRepository, TaskCardDetailRepository taskCardDetailRepository, TaskService taskService, TimeSheetRepository timeSheetRepository, EmployeeFeign employeeFeign) {
        this.taskCardRepository = taskCardRepository;
        this.rateCardDetailRepository = rateCardDetailRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskCardDetailRepository = taskCardDetailRepository;
        this.taskService = taskService;
        this.timeSheetRepository = timeSheetRepository;


        this.employeeFeign = employeeFeign;
    }


    @Override
    public Set<TaskCard> updateTaskCard(Set<TaskCard> taskCards, TimeSheetStatus status) {
        List<TaskCard> taskCardList = new ArrayList<>();

        taskCards.forEach(taskCard -> {
            if (!taskCardRepository.existsById(taskCard.getId()))
                throw new ResourceNotFoundException("TaskCard", "ID", taskCard.getId());
            taskCard.setStatus(status);
            TimeSheet timeSheet = timeSheetRepository.findById(taskCard.getTimeSheet().getId()).orElseThrow(() -> new ResourceNotFoundException("TimeSheet", "ID", taskCard.getTimeSheet().getId()));
            TaskCard savedTaskCard = taskCardRepository.save(calculateAmount(taskCard));
            taskCardList.add(savedTaskCard);
            if (status.equals(TimeSheetStatus.APPROVED)) {
                taskCardDetailRepository.setStatusForTaskCardDetail(TimeSheetStatus.APPROVED.name(), taskCard.getId());
                if (taskCard.getType().equals(TaskType.BILLABLE))
                    taskService.updateTaskHours(taskCard.getTask().getId(), taskCard.getHours());
//                timeSheet status update
                TimeSheetStatus(timeSheet);
            } else {
                taskCardDetailRepository.setStatusForTaskCardDetail(TimeSheetStatus.REJECTED.name(), taskCard.getId());
                timeSheetRepository.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
            }
        });
        Set<TaskCard> cardSet = taskCardList.stream().collect(Collectors.toCollection(HashSet::new));
        return cardSet;
    }

    @Override
    public Set<TaskCard> getAllTaskCardByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<TaskCard> taskCardList = taskCardRepository.findAll(pageable).getContent();
        Set<TaskCard> taskCardSet = taskCardList.stream().collect(Collectors.toCollection(HashSet::new));
        return taskCardSet;

    }

    @Override
    public void deleteTaskCardById(Long id) {
        if (!taskCardRepository.existsById(id))
            throw new ResourceNotFoundException("TaskCard", "ID", id);
        taskCardRepository.deleteById(id);
    }

    @Override
    public Set<TaskCard> getAllTaskCardByMangerId(Long managerId) {
        Set<Project> projectSet = projectRepository.findAllByManagerId(managerId);

        return taskCardRepository.findByStatusNotLikeAndProjectIn(TimeSheetStatus.PENDING, projectSet);
    }

    @Override
    public List<TaskCard> getAllTaskCardByProject(Long projectId) {
        return taskCardRepository.findByStatusNotLikeAndProject_IdOrderByEmployeeIdAscTaskCardDetailsAsc(TimeSheetStatus.PENDING, projectId);
    }

    @Override
    public TaskCard getTaskCardById(Long id) {
        return taskCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TaskCard", "ID", id));
    }

    public TaskCard calculateAmount(TaskCard card) {

//      Get rateCardId
        Task task = taskRepository.findById(card
                .getTask().getId()).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", card.getTask().getId()));


        Long rateCardId = new Long(0);
        BigDecimal ratePerHour = new BigDecimal(0);

        if (TaskType.BILLABLE.equals(task.getType())) {
            if (task.getProject().getRateCard() != null)
                rateCardId = task.getProject().getRateCard().getId();


//      Assign ratePerHour
            if (card.getLocation().getId() != null && card.getSkillId() != null && card.getEmployeeId() != null) {
//      Get Employee Designation
                Long designationId = Long.valueOf(1);
                EmployeeDto employeeDto = employeeFeign.getEmployeeById(card.getEmployeeId()).getBody();
                if (employeeDto.getDesignation() != null)
                    designationId = employeeDto.getDesignation().getId();
//       TODo Add RateCard Id
                RateCardDetail rateCardDetail = rateCardDetailRepository.findByLocationIdAndSkillIdAndDesignationId
                        (card.getLocation().getId(), card.getSkillId(), designationId);
                if (rateCardDetail != null)
                    ratePerHour = rateCardDetail.getValue();

                System.out.println(ratePerHour);

                card.setRatePerHour(ratePerHour);
            }
        }

//      sum of hours
        BigDecimal hours = new BigDecimal(0);
        Set<TaskCardDetail> cardDetails = new HashSet<>(card.getTaskCardDetails());
        for (TaskCardDetail cardDetail : cardDetails) {
            hours = hours.add(cardDetail.getHours());
        }

//      add task_card_id
        cardDetails.forEach(taskCardDetail -> card.add(taskCardDetail));

//      SetAmount and Hour
        card.setHours(hours);
        card.setAmount(Reusable.multiplyRates(hours, ratePerHour));
        card.setTask(task);
        return card;
    }

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
            timeSheetRepository.setStatusForTimeSheet(TimeSheetStatus.APPROVED.name(), timeSheet.getId());
        } else if (taskCardReject > 1) {
            timeSheetRepository.setStatusForTimeSheet(TimeSheetStatus.REJECTED.name(), timeSheet.getId());
        }

    }


}

