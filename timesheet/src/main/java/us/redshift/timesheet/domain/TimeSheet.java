package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pss_timesheets")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "taskCards")
public class TimeSheet extends BaseEntity {

    private String name;

    private String comment;

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status;

    private Long approvedBy;

    private Long submittedBy;

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    private Date toDate;


    @JsonIgnoreProperties(value = "timeSheet")
    @OneToMany(mappedBy = "timeSheet", cascade = {CascadeType.MERGE})
    private Set<TaskCard> taskCards = new HashSet<>();

//    public void add(TaskCard taskCard) {
//        taskCard.setTimeSheet(this);
//        this.taskCards.add(taskCard);
//    }

    public void setStatus(TimeSheetStatus status) {
        if (status == null)
            status = TimeSheetStatus.SUBMITTED;
        this.status = status;
    }

}
