package us.redshift.timesheet.domain.taskcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private BigDecimal hours;

    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status;

    private String comment="";

    private String rejectedComment="";

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "task_card_id")
    @JsonIgnoreProperties(value = {"taskCardDetails","project","client"})
    private TaskCard taskCard;

    public void setStatus(TimeSheetStatus status) {
        if (status == null)
            status = TimeSheetStatus.PENDING;
        this.status = status;
    }
}