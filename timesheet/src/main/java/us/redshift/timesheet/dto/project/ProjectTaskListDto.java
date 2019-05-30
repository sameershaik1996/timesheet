package us.redshift.timesheet.dto.project;

import lombok.*;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.CommonDto;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskListDto extends BaseDto {

    @NonNull
    private Long id;
    private String taskCode;
    private String name;
    private Date startDate;
    private Date endDate;
    private BigDecimal usedHour;
    private BigDecimal billableHour;
    private TaskType type;
    private TaskStatus status;
    private CommonDto project;
    private CommonDto client;
}
