package us.redshift.timesheet.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.RateCardDetail;
import us.redshift.timesheet.domain.Task;
import us.redshift.timesheet.domain.TaskCard;
import us.redshift.timesheet.domain.TaskCardDetail;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.RateCardDetailRepository;
import us.redshift.timesheet.reposistory.TaskCardRepository;
import us.redshift.timesheet.reposistory.TaskRepository;
import us.redshift.timesheet.util.Reusable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskCardService implements ITaskCardService {

    private final TaskCardRepository taskCardRepository;

    private final RateCardDetailRepository rateCardDetailRepository;

    private final TaskRepository taskRepository;

    public TaskCardService(TaskCardRepository taskCardRepository, RateCardDetailRepository rateCardDetailRepository, TaskRepository taskRepository) {
        this.taskCardRepository = taskCardRepository;
        this.rateCardDetailRepository = rateCardDetailRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskCard> saveTaskCard(List<TaskCard> taskCards) {
        List<TaskCard> taskCardSet = new ArrayList<>();
        taskCards.forEach(taskCard -> taskCardSet.add(calculateAmount(taskCard)));

        return taskCardRepository.saveAll(taskCardSet);
    }

    @Override
    public List<TaskCard> updateTaskCard(List<TaskCard> taskCards) {
        List<TaskCard> taskCardSet = new ArrayList<>();
        taskCards.forEach(taskCard -> {
            if (!taskCardRepository.existsById(taskCard.getId()))
                new ResourceNotFoundException("TaskCard", "ID", taskCard.getId());
            taskCardSet.add(calculateAmount(taskCard));
        });
        return taskCardRepository.saveAll(taskCardSet);
    }

    @Override
    public List<TaskCard> getAllTaskCardByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return taskCardRepository.findAll(pageable).getContent();
    }

    @Override
    public void deleteTaskCardById(Long id) {
        if (!taskCardRepository.existsById(id))
            new ResourceNotFoundException("TaskCard", "ID", id);
        taskCardRepository.deleteById(id);
    }

    @Override
    public TaskCard getTaskCard(Long id) {
        return taskCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TaskCard", "ID", id));
    }

    private TaskCard calculateAmount(TaskCard card) {

//      Get rateCardId
        Task task = taskRepository.findById(card
                .getTask().getId()).orElseThrow(() -> new ResourceNotFoundException("Task", "Id", card.getTask().getId()));


        Long rateCardId = new Long(0);
        if (task.getProject().getRateCard() != null)
            rateCardId = task.getProject().getRateCard().getId();

//      Assign ratePerHour
        BigDecimal ratePerHour = new BigDecimal(0);
        if (card.getLocationId() != null && card.getSkillId() != null) {
            RateCardDetail rateCardDetail = rateCardDetailRepository.findByRateCard_IdAndLocationIdAndSkillIdAndDesignationId
                    (rateCardId, card.getLocationId(), card.getSkillId(), Long.valueOf(1));
            if (rateCardDetail != null)
                ratePerHour = rateCardDetail.getValue();

            System.out.println(ratePerHour);

            card.setRatePerHour(ratePerHour);
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
}
