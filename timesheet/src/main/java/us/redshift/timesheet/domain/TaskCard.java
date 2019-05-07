package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "taskCardDetails")
public class TaskCard extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status;

    @Enumerated(EnumType.STRING)
    private TaskType type = TaskType.BILLABLE;

    private Long employeeId;
    private Long skillId;
    private Long locationId;

    private BigDecimal ratePerHour;

    private BigDecimal amount;


    private String comment;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @OneToMany(mappedBy = "taskCard",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("taskCard")
    private List<TaskCardDetail> taskCardDetails = new ArrayList<>();

    @ManyToOne
    @JsonIgnoreProperties("taskCards")
    @JoinColumn(name = "time_sheet_id")
    private TimeSheet timeSheet;

    public void add(TaskCardDetail taskCardDetail) {
        this.taskCardDetails.add(taskCardDetail);
        taskCardDetail.setTaskCard(this);


    }

}
