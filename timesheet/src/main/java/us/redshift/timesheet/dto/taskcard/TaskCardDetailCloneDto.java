package us.redshift.timesheet.dto.taskcard;

import lombok.*;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardDetailCloneDto {

    private Long _index;
    @NonNull
    private Date date;
    @NonNull
    private BigDecimal hours;
    private TimeSheetStatus status = TimeSheetStatus.PENDING;


}
