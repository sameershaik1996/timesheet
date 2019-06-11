package us.redshift.timesheet.domain.taskcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pss_task_card_details")
@Getter
@Setter
@NoArgsConstructor
public class TaskCardDetail extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date date;


    private Long _index;

    private BigDecimal hours;

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status = TimeSheetStatus.PENDING;

    private String comment = "";

    private String rejectedComment = "";

    @ManyToOne()
    @JoinColumn(name = "task_card_id")
//    @JsonIgnoreProperties(value = {"taskCardDetails"})
    @JsonIgnore
    private TaskCard taskCard;


    public void setComment(String comment) {
        this.comment = comment == null ? "" : comment;
    }

    public void setRejectedComment(String rejectedComment) {
        this.rejectedComment = rejectedComment == null ? "" : rejectedComment;
    }

    public void setStatus(TimeSheetStatus status) {
        this.status = status == null ? TimeSheetStatus.PENDING : status;
    }
}
