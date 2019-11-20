package us.redshift.timesheet.domain.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "pss_timeoff_dates")
public class TimeOffDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;


    @ManyToOne()
    @JoinColumn(name = "time_off_id")
    @JsonIgnore
    private TimeOff timeOff;
}
