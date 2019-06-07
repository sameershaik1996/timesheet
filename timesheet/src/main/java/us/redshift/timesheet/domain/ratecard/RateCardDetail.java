package us.redshift.timesheet.domain.ratecard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.EmployeeRole;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.common.Location;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pss_rate_card_details", uniqueConstraints =
@UniqueConstraint(columnNames = {"employee_role_id", "location_id", "rate_card_id"}))
@Getter
@Setter
@NoArgsConstructor
public class RateCardDetail extends BaseEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    /*@Column(name = "skill_id", nullable = false)
    private Long skillId;
    @Column(name = "designation_id", nullable = false)
    private Long designationId;*/

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "employee_role_id", nullable = false)
    private EmployeeRole employeeRole;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rate_card_id", nullable = false)
    @JsonIgnoreProperties(value = "rateCardDetails")
    private RateCard rateCard;

}
