package us.redshift.timesheet.domain.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pss_time_offs")
@Getter
@Setter
@NoArgsConstructor
public class TimeOff extends BaseEntity {

    private Long employeeId;
    private String reason;
    private Date date;
    private BigDecimal hours;
    private TimeSheetStatus status;

    @ManyToOne()
    @JoinColumn(name = "time_sheet_id")
    @JsonIgnoreProperties(value = {"timeOffs", "taskCards"})
    private TimeSheet timeSheet;

    public void setStatus(TimeSheetStatus status) {
        this.status = status == null ? TimeSheetStatus.PENDING : status;
    }
}
