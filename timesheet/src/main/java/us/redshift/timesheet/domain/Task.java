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

    @Column(unique = true)
    private String name;

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

    private BigDecimal nBillableHour;

    private BigDecimal usedHour;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "pss_tasks_skill")
    private Set<Long> skillId;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "pss_tasks_employee")
    private Set<Long> employeeId;

    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;


    public void setStatus(TaskStatus status) {
        if (status == null)
            status = TaskStatus.UNASSIGNED;
        this.status = status;
    }

    public void setType(TaskType type) {
        if (type == null)
            type = TaskType.BILLABLE;
        this.type = type;
    }

}
