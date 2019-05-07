package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task_card_details")
@Getter
@Setter
@NoArgsConstructor
public class TaskCardDetail extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date date;

    private Long hours;

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status;

    private String comment;

    private String rejectedComment;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "task_card_id", nullable = false)
    @JsonIgnoreProperties({"taskCardDetails"})
    private TaskCard taskCard;

}
