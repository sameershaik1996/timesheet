package us.redshift.timesheet.domain.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.taskcard.TaskCard;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "pss_timesheets",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"employee_id", "from_date", "to_date", "week_number", "year"}))
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "taskCards")
public class TimeSheet extends BaseEntity {

    private String name;

    private String comment = "";

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status = TimeSheetStatus.PENDING;

//    private Long approverId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "from_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "to_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;
    @Column(name = "year", nullable = false)
    private Integer year;


    @JsonIgnoreProperties(value = "timeSheet", allowSetters = true)
    @OneToMany(mappedBy = "timeSheet", cascade = {CascadeType.ALL})
    private List<TaskCard> taskCards = new ArrayList<>();

    @JsonIgnoreProperties(value = "timeSheet", allowSetters = true)
    @OneToMany(mappedBy = "timeSheet", cascade = {CascadeType.ALL})
    private Set<TimeOff> timeOffs = new HashSet<>();


    public void addTaskCard(TaskCard taskCard) {
        taskCard.setTimeSheet(this);
        this.taskCards.add(taskCard);
    }

    public void addTimeOff(TimeOff timeOff) {
        timeOff.setTimeSheet(this);
        this.timeOffs.add(timeOff);
    }

}
