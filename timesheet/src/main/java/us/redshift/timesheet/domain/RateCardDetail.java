package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rate_card_details", uniqueConstraints = {@UniqueConstraint(columnNames = {"skill_id", "designation_id", "location_id"})})
@Getter
@Setter
@NoArgsConstructor
public class RateCardDetail extends BaseEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;
    @Column(name = "skill_id")
    private Long skillId;
    @Column(name = "designation_id")
    private Long designationId;
    @Column(name = "location_id")
    private Long locationId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "rate_card_id", nullable = false)
    @JsonIgnoreProperties({"rateCard", "rateCardDetails"})
    private RateCard rateCard;

}
