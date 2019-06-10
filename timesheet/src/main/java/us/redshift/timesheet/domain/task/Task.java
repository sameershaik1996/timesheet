package us.redshift.timesheet.domain.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.Employee;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.taskcard.TaskType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
    private TaskStatus status = TaskStatus.ACTIVE;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date startedOn;

    @Temporal(TemporalType.DATE)
    private Date endedOn;


    private BigDecimal billableHour = BigDecimal.valueOf(0);

    private BigDecimal nonBillableHour = BigDecimal.valueOf(0);

    private BigDecimal usedHour = BigDecimal.valueOf(0);

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "pss_task_skills")
    @JoinColumn(nullable = false)
    private List<Long> skillId;

//    @ElementCollection(targetClass = Long.class)
//    @JoinTable(name = "pss_task_employees")
//    @JoinColumn(nullable = false)
//    private List<Long> employeeId;

    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    @JsonIgnoreProperties(value = "task")
    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL})
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employee.setTask(this);
        employees.add(employee);
    }

}
