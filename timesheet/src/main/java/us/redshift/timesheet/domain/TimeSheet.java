package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "timesheets")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "taskCards")
public class TimeSheet extends BaseEntity {

    private String name;

    private String comment;

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status = TimeSheetStatus.SUBMITTED;

    private Long approverId;

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    private Date toDate;


    @JsonIgnoreProperties("timeSheet")
    @OneToMany(mappedBy = "timeSheet", cascade = {CascadeType.MERGE})
    private List<TaskCard> taskCards;

    public void add(TaskCard taskCard) {
        this.taskCards.add(taskCard);
        taskCard.setTimeSheet(this);

    }

}
