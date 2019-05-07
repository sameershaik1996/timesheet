package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.TaskStatus;
import us.redshift.timesheet.domain.TaskType;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskListDto extends BaseDto {

    private String description;
    private TaskType type;
    private TaskStatus status;
    private Date startDate;
    private Date endDate;
    private BigDecimal billableHour;
    private BigDecimal usedHours;
}
