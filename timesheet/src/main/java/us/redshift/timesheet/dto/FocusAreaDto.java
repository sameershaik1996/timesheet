package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FocusAreaDto extends BaseDto {

    private Long id;
    private String code;
    private String name;
}
