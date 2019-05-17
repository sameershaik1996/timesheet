package us.redshift.timesheet.dto.ratecard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.project.ProjectType;

@Getter
@Setter
@NoArgsConstructor
public class RateCardDto extends BaseEntity {

    private String name;
    private Boolean isDefault = false;
    private ProjectType projectType;
//    private Set<RateCardDetailDto> rateCardDetails = new HashSet<>();


}
