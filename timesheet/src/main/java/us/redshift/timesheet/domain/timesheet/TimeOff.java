package us.redshift.timesheet.domain.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.common.TimeOffReasons;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pss_time_offs")
@Getter
@Setter
@NoArgsConstructor
public class TimeOff extends BaseEntity {

    private Long employeeId;
    private TimeOffReasons reason;
    @JsonIgnoreProperties(value = "timeOff", allowSetters = true)
    @OneToMany(mappedBy = "timeOff", cascade = {CascadeType.ALL})
    private List<TimeOffDate> dates=new ArrayList<>();
    private BigDecimal hours;

    private TimeSheetStatus status;

    @ManyToOne()
    @JoinColumn(name = "time_sheet_id")
    @JsonIgnore
    private TimeSheet timeSheet;

    public void add(TimeOffDate timeOffDate) {
//        this.taskCardDetails.add(taskCardDetail);
        timeOffDate.setTimeOff(this);
    }
}
