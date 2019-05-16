package us.redshift.timesheet.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.dto.common.BaseDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndustryDto extends BaseDto {

    private Long id;
    private String name;
}
