package us.redshift.timesheet.dto.client;

import lombok.*;
import us.redshift.timesheet.dto.common.BaseDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PocDto extends BaseDto {

    private Long id;
    @NotNull(message = "name cannot be empty")
    private String name;
    @NotNull(message = "email cannot be empty")
    private String email;
    @NotNull(message = "phoneNumber cannot be empty")
    @Size(min = 10, max = 10, message = "Phone Number Can't exceed more than 10 digits")
    private String phoneNumber;
}
