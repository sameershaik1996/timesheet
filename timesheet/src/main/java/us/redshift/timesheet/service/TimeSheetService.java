package us.redshift.timesheet.service;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.TaskCard;
import us.redshift.timesheet.domain.TimeSheet;
import us.redshift.timesheet.reposistory.RateCardDetailRepository;
import us.redshift.timesheet.reposistory.TaskCardRepository;
import us.redshift.timesheet.reposistory.TaskRepository;
import us.redshift.timesheet.reposistory.TimeSheetRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSheetService implements ITimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final RateCardDetailRepository rateCardDetailRepository;
    private final TaskRepository taskRepository;
    private final TaskCardRepository taskCardRepository;


    public TimeSheetService(TimeSheetRepository timeSheetRepository, RateCardDetailRepository rateCardDetailRepository, TaskRepository taskRepository, TaskCardRepository taskCardRepository) {
        this.timeSheetRepository = timeSheetRepository;
        this.rateCardDetailRepository = rateCardDetailRepository;
        this.taskRepository = taskRepository;
        this.taskCardRepository = taskCardRepository;
    }


    @Override
    public TimeSheet saveTimeSheet(TimeSheet timeSheet) {

        TimeSheet savedTimeSheet = timeSheetRepository.save(timeSheet);
        List<TaskCard> taskCards = new ArrayList<>(timeSheet.getTaskCards());
        taskCards.forEach(taskCard -> {
            TaskCard card = taskCardRepository.findById(taskCard.getId()).get();
            card.setTimeSheet(savedTimeSheet);
            taskCardRepository.save(card);
        });
        return timeSheet;
    }

    @Override
    public TimeSheet updateTimeSheet(TimeSheet timeSheet) {
        return null;
    }

    @Override
    public List<TimeSheet> getAllTimeSheet() {
        return null;
    }

    @Override
    public TimeSheet getTimeSheet(Long id) {
        return null;
    }
}
