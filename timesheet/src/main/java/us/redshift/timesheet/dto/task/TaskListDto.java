package us.redshift.timesheet.dto.task;

import lombok.*;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.SkillDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskListDto extends BaseDto {

    @NonNull
    private Long id;
    private String taskCode;
    private String name;
    private String description;
    private TaskType type;
    private TaskStatus status;
    private Date startDate;
    private Date endDate;
    private BigDecimal billableHour;
    private BigDecimal nonBillableHour;
    private BigDecimal usedHour;
    private List<SkillDto> skills;
}
