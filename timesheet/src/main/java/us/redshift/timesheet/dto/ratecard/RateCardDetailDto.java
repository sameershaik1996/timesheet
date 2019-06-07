package us.redshift.timesheet.dto.ratecard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.EmployeeRole;
import us.redshift.timesheet.domain.common.Location;
import us.redshift.timesheet.dto.common.BaseDto;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class RateCardDetailDto extends BaseDto {

    private Long id;
    private BigDecimal value;
   /* private long skillId;
    private long designationId;
    private long locationId;*/

    private Location location;
    private EmployeeRole employeeRole;

    private RateCardDto rateCard;


}
