package us.redshift.timesheet.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "taskCards")
public class Task extends BaseEntity {

    //    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type = TaskType.BILLABLE;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date startedOn;

    @Temporal(TemporalType.DATE)
    private Date endedOn;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "task_skill_mapping")
    private Set<Long> skillId;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "task_employee_mapping")
    private Set<Long> employeeId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}
