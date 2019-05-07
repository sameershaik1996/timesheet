package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pss_rate_cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "rateCardDetails")
public class RateCard extends BaseEntity {

    private String name;

    private Boolean isDefault = false;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @JsonIgnoreProperties(value = "rateCard")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rateCard")
    private Set<RateCardDetail> rateCardDetails = new HashSet<>();

    public void setProjectType(ProjectType projectType) {
        if (projectType == null)
            projectType = ProjectType.FIXED_BID;
        this.projectType = projectType;
    }

    public void addRateCardDetail(RateCardDetail rateCardDetail) {
        rateCardDetail.setRateCard(this);
        rateCardDetails.add(rateCardDetail);
    }


}
