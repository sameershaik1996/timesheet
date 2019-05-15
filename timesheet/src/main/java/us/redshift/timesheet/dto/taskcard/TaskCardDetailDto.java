package us.redshift.timesheet.dto.taskcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskCardDetailDto extends BaseDto {

    private Long id;
    @NonNull
    private Date date;
    @NonNull
    private BigDecimal hours;
    private TimeSheetStatus status;
    private String comment;
    private String rejectedComment;
    @NonNull
    @JsonIgnoreProperties("taskCardDetails")
    private TaskCardDto taskCard;

}
