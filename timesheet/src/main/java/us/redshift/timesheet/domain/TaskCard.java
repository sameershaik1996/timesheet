package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pss_task_cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "taskCardDetails")
public class TaskCard extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    private Long employeeId;
    private Long skillId;
    private Long locationId;

    private BigDecimal ratePerHour;

    private BigDecimal amount;

    private BigDecimal hours;


    private String comment;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "task_id")
    private Task task;

    @OneToMany(mappedBy = "taskCard",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "taskCard")
    private Set<TaskCardDetail> taskCardDetails = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "time_sheet_id")
    private TimeSheet timeSheet;

    public void add(TaskCardDetail taskCardDetail) {
        this.taskCardDetails.add(taskCardDetail);
        taskCardDetail.setTaskCard(this);
    }

    public void setStatus(TimeSheetStatus status) {
        if (status == null)
            status = TimeSheetStatus.PENDING;
        this.status = status;
    }

    public void setType(TaskType type) {
        if (type == null)
            type = TaskType.BILLABLE;
        this.type = type;
    }

}