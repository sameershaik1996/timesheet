package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rate_cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "rateCardDetails")
public class RateCard extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ProjectType projectType = ProjectType.FIXED;

    @JsonIgnoreProperties("rateCard")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rateCard")
    private List<RateCardDetail> rateCardDetails = new ArrayList<>();


    public void addRateCardDetail(RateCardDetail rateCardDetail) {

        rateCardDetail.setRateCard(this);
        rateCardDetails.add(rateCardDetail);
    }


}
