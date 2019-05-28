package us.redshift.timesheet.domain.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.taskcard.TaskType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "pss_tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String taskCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date startedOn;

    @Temporal(TemporalType.DATE)
    private Date endedOn;


    private BigDecimal billableHour;

    private BigDecimal nonBillableHour;

    private BigDecimal usedHour;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "pss_task_skills")
    @JoinColumn(nullable = false)
    private List<Long> skillId;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "pss_task_employees")
    @JoinColumn(nullable = false)
    private List<Long> employeeId;

    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    public void setStatus(TaskStatus status) {
        this.status = status == null ? TaskStatus.ACTIVE : status;
    }

    public void setType(TaskType type) {
        this.type = type == null ? TaskType.BILLABLE : type;
    }

    public void setUsedHour(BigDecimal usedHour) {
        this.usedHour = usedHour == null ? BigDecimal.valueOf(0) : usedHour;
    }

    public void setBillableHour(BigDecimal billableHour) {
        this.billableHour = billableHour == null ? BigDecimal.valueOf(0) : billableHour;
    }

    public void setNonBillableHour(BigDecimal nonBillableHour) {
        this.nonBillableHour = nonBillableHour == null ? BigDecimal.valueOf(0) : nonBillableHour;
    }
}
