package us.redshift.timesheet.dto.client;

import lombok.*;
import us.redshift.timesheet.dto.common.BaseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PocDto extends BaseDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
