package us.redshift.timesheet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


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
    private Set<Long> skillId;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "pss_task_employees")
    @JoinColumn(nullable = false)
    private Set<Long> employeeId;

    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    public void setStatus(TaskStatus status) {
        if (status == null)
            status = TaskStatus.ACTIVE;
        this.status = status;
    }

    public void setType(TaskType type) {
        if (type == null)
            type = TaskType.BILLABLE;
        this.type = type;
    }

    public void setBillableHour(BigDecimal billableHour) {
        if (billableHour == null)
            this.billableHour = new BigDecimal(0);
        this.billableHour = billableHour;
    }

    public void setNonBillableHour(BigDecimal nonBillableHour) {
        if (nonBillableHour == null)
            this.nonBillableHour = new BigDecimal(0);
        this.nonBillableHour = nonBillableHour;
    }

    public void setUsedHour(BigDecimal usedHour) {
        if (usedHour == null)
            this.usedHour = new BigDecimal(0);
        this.usedHour = usedHour;
    }
}
