package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.TaskCardDetail;
import us.redshift.timesheet.domain.TaskType;
import us.redshift.timesheet.domain.TimeSheetStatus;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardDto extends BaseDto {


    private String name;
    private TimeSheetStatus status;
    private TaskType type;
    private CommonDto employee;
    private SkillDto skill;
    private CommonDto locationId;
    private BigDecimal ratePerHour;
    private BigDecimal amount;
    private BigDecimal hours;
    private String comment;
    private TaskListDto task;
    private Set<TaskCardDetail> taskCardDetails;
}
