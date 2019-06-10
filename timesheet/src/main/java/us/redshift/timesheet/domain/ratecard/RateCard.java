package us.redshift.timesheet.domain.ratecard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pss_rate_cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "rateCardDetails")
public class RateCard extends BaseEntity {

    private String name;

    private Boolean isDefault = false;


    @JsonIgnoreProperties(value = "rateCard")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rateCard")
    private List<RateCardDetail> rateCardDetails = new ArrayList<>();


    public void addRateCardDetail(RateCardDetail rateCardDetail) {
        rateCardDetail.setRateCard(this);
    }

}
