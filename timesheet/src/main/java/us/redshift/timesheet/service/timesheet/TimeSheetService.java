package us.redshift.timesheet.service.timesheet;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.reposistory.taskcard.TaskCardRepository;
import us.redshift.timesheet.reposistory.TaskRepository;
import us.redshift.timesheet.reposistory.TimeSheetRepository;
import us.redshift.timesheet.util.Reusable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TimeSheetService implements ITimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final TaskCardDetailRepository taskCardDetailRepository;
    private final TaskRepository taskRepository;
    private final TaskCardRepository taskCardRepository;


    public TimeSheetService(TimeSheetRepository timeSheetRepository, TaskCardDetailRepository taskCardDetailRepository, TaskRepository taskRepository, TaskCardRepository taskCardRepository) {
        this.timeSheetRepository = timeSheetRepository;
        this.taskCardDetailRepository = taskCardDetailRepository;
        this.taskRepository = taskRepository;
        this.taskCardRepository = taskCardRepository;
    }


    @Override
    public TimeSheet saveTimeSheet(TimeSheet timeSheet) {

        TimeSheet savedTimeSheet = timeSheetRepository.save(timeSheet);
        Set<TaskCard> taskCards = new HashSet<>(timeSheet.getTaskCards());
        taskCards.forEach(taskCard -> {
            taskCardRepository.setTimeSheetIdForTaskCard(savedTimeSheet.getId(), taskCard.getId());
            taskCardRepository.setStatusForTaskCard(TimeSheetStatus.SUBMITTED.name(), taskCard.getId());
            taskCardDetailRepository.setStatusForTaskCardDetail(TimeSheetStatus.SUBMITTED.name(), taskCard.getId());
        });
        return savedTimeSheet;
    }

    @Override
    public TimeSheet updateTimeSheet(TimeSheet timeSheet, String status) {
        if (!timeSheetRepository.existsById(timeSheet.getId()))
            throw new ResourceNotFoundException("TimeSheet", "Id", timeSheet.getId());
        if (status.equalsIgnoreCase(TimeSheetStatus.REJECTED.name()))
            timeSheet.setStatus(TimeSheetStatus.REJECTED);
        else
            timeSheet.setStatus(TimeSheetStatus.APPROVED);
        return timeSheetRepository.save(timeSheet);
    }

    @Override
    public Set<TimeSheet> getAllTimeSheetByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll(pageable).getContent();
        Set<TimeSheet> timeSheetSet = timeSheetList.stream().collect(Collectors.toCollection(HashSet::new));
        return timeSheetSet;
    }

    @Override
    public TimeSheet getTimeSheet(Long id) {
        return timeSheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TimeSheet", "Id", id));
    }

}
