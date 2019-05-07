package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.DateUtility;
import us.redshift.timesheet.domain.TaskCard;
import us.redshift.timesheet.domain.TaskCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.RateCardDetailRepository;
import us.redshift.timesheet.reposistory.TaskCardDetailRepository;
import us.redshift.timesheet.reposistory.TaskCardRepository;
import us.redshift.timesheet.reposistory.TaskRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskCardService implements ITaskCardService {

    private final TaskCardRepository taskCardRepository;

    private final RateCardDetailRepository rateCardDetailRepository;

    private final TaskCardDetailRepository taskCardDetailRepository;

    private final TaskRepository taskRepository;

    public TaskCardService(TaskCardRepository taskCardRepository, RateCardDetailRepository rateCardDetailRepository, TaskRepository taskRepository,TaskCardDetailRepository taskCardDetailRepository) {
        this.taskCardRepository = taskCardRepository;
        this.rateCardDetailRepository = rateCardDetailRepository;
        this.taskRepository = taskRepository;
        this.taskCardDetailRepository =taskCardDetailRepository;
    }

    @Override
    public TaskCard saveTaskCard(TaskCard taskCard) {


/*        Task task = taskRepository.findById(taskCard
                .getTask().getId()).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", taskCard
                .getTask().getId()));
        Long rateCardId = task.getProject().getRateCard().getId();

//        Assign ratePerHour
        BigDecimal ratePerHour = new BigDecimal(0);
        if (taskCard.getLocationId() != null && taskCard.getSkillId() != null) {
            ratePerHour = rateCardDetailRepository.findByRatecardIdAndLocationIdAndSkillIdAndDesignationId
                    (rateCardId, taskCard.getLocationId(), taskCard.getSkillId(), Long.valueOf(1)).getValue();
            taskCard.setRatePerHour(ratePerHour);
        }*/

        //sum of hours
//        Long hours = new Long(0);
        List<TaskCardDetail> taskCardDetails = new ArrayList<>(taskCard.getTaskCardDetails());
//        for (TaskCardDetail cardDetail : taskCardDetails) {
//            hours += cardDetail.getHours();
//        }

        //add task_card_id
        taskCardDetails.forEach(taskCardDetail -> taskCard.add(taskCardDetail));

/*//       SetAmount and Hour
        taskCard.setHours(hours);
        taskCard.setAmount(ratePerHour.multiply(BigDecimal.valueOf(hours)));*/


        return taskCardRepository.save(taskCard);
    }

    @Override
    public TaskCard updateTaskCard(TaskCard taskCard) {
        taskCardRepository.findById(taskCard.getId()).orElseThrow(() -> new ResourceNotFoundException("TaskCard", "ID", taskCard.getId()));
        return taskCardRepository.save(taskCard);
    }

    @Override
    public List<TaskCard> getAllTaskCard() {
        List<TaskCard> taskCards = new ArrayList<>();
        taskCardRepository.findAll().iterator().forEachRemaining(taskCards::add);
        return taskCards;
    }

    @Override
    public TaskCard getTaskCard(Long id) {
        return taskCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TaskCard", "ID", id));
    }

    @Override
    public List<TaskCard> copyTaskCards(Instant dateFrom, Instant dateTo) {
        List<TaskCard> cards= getCards(dateFrom,dateTo);
        System.out.println(cards);
        List<TaskCard> newCards=new ArrayList<>();
        cards.forEach(card->{
            TaskCard newCard=new TaskCard();
            newCard.setComment("");
            newCard.setAmount(card.getAmount());
            newCard.setEmployeeId(card.getEmployeeId());
            newCard.setLocationId(card.getLocationId());
            newCard.setRatePerHour(card.getRatePerHour());
            newCard.setSkillId(card.getSkillId());
            newCard.setType(card.getType());
            newCard.setTask(card.getTask());
            List<TaskCardDetail>  newCardDetails=new ArrayList<>();
            card.getTaskCardDetails().forEach(taskCardDetail -> {
                TaskCardDetail  newCardDetail=new TaskCardDetail();
                newCardDetail.setComment("");
                newCardDetail.setRejectedComment("");
                LocalDate ld=DateUtility.convertToLocalDateViaInstant(taskCardDetail.getDate());
                System.out.println(ld);
                ld.plusDays(7);
                System.out.println(ld);
                Date d= DateUtility.convertToDateViaInstant(ld);
                System.out.println(d);
                newCardDetail.setDate(d);
                //taskCardDetail.setId(null);
                newCardDetails.add(newCardDetail);
                newCard.add(newCardDetail);
            });

            newCards.add(newCard);
        });
        return taskCardRepository.saveAll(newCards);
    }

    private List<TaskCard> getCards(Instant dateFrom, Instant dateTo) {
        List<TaskCard> newCards = new ArrayList<>();
        List<TaskCard> cards= taskCardRepository.findByDateBetween(dateFrom,dateTo);
        cards.forEach(card->{
            List<TaskCardDetail> cardDetails=taskCardDetailRepository.findByDateBetweenAndTaskCardId(Date.from(dateFrom),Date.from(dateTo),card.getId());
            card.setTaskCardDetails(cardDetails);
            newCards.add(card);
        });
        return newCards;
    }
}
