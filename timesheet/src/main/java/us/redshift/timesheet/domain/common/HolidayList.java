package us.redshift.timesheet.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pss_holidays")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HolidayList {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(unique = true)
    private Date date;


    private String description;


}
