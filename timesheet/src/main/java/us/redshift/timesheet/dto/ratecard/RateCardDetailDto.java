package us.redshift.timesheet.dto.ratecard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.CommonDto;
import us.redshift.timesheet.dto.common.DesignationDto;
import us.redshift.timesheet.dto.common.SkillDto;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class RateCardDetailDto extends BaseDto {

    private BigDecimal value;
    private SkillDto skill;
    private DesignationDto designation;
    private CommonDto location;


}
