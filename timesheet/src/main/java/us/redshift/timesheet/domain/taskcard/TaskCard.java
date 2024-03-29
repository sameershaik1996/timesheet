package us.redshift.timesheet.domain.taskcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.EmployeeRole;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.common.Location;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.task.Task;
import us.redshift.timesheet.domain.timesheet.TimeSheet;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pss_task_cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "taskCardDetails")
public class TaskCard extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status = TimeSheetStatus.PENDING;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType type;

    private Long approverId;


    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "employee_role_id")
    private EmployeeRole role;

    private Long skillId;
    private Long designationId;


    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private BigDecimal ratePerHour;

    private BigDecimal amount;

    private BigDecimal hours;

    private String comment;


    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne()
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @OneToMany(mappedBy = "taskCard",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "taskCard")
    private List<TaskCardDetail> taskCardDetails = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "time_sheet_id")
    @JsonIgnoreProperties(value = "taskCards")
    private TimeSheet timeSheet;

    public void add(TaskCardDetail taskCardDetail) {
//        this.taskCardDetails.add(taskCardDetail);
        taskCardDetail.setTaskCard(this);
    }

}