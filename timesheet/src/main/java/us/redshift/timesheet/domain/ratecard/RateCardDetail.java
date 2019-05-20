package us.redshift.timesheet.domain.ratecard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.common.Location;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pss_rate_card_details")
@Getter
@Setter
@NoArgsConstructor
public class RateCardDetail extends BaseEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    @Column(name = "skill_id", nullable = false)
    private Long skillId;
    @Column(name = "designation_id", nullable = false)
    private Long designationId;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "rate_card_id", nullable = false)
    private RateCard rateCard;

}