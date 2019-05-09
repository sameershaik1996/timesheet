package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndustryDto extends BaseDto {

    private Long id;
    private String name;
}
