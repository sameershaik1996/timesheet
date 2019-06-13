package us.redshift.timesheet.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.dto.common.BaseDto;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FocusAreaDto extends BaseDto {

    private Long id;
    @NotNull(message = "code cannot be empty")
    private String code;
    @NotNull(message = "name cannot be empty")
    private String name;
}
