package us.redshift.timesheet.dto.taskcard;

import lombok.*;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskCardDetailDto extends BaseDto {

    private Long id;
    private Long _index;
    private Date date;
    private BigDecimal hours;
    private TimeSheetStatus status = TimeSheetStatus.PENDING;
    @NotNull(message = "comment cannot be empty")
    private String comment = "";
    private String rejectedComment = "";
    private TaskCardListDto taskCard;

}
