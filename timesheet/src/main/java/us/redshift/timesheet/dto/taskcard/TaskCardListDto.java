package us.redshift.timesheet.dto.taskcard;

import lombok.*;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.*;
import us.redshift.timesheet.dto.task.TaskListDto;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardListDto extends BaseDto {

    @NonNull
    private Long id;
    private TimeSheetStatus status;
    private EmployeeListDto approvedBy;
    private TaskType type;
    private EmployeeListDto employee;
    private SkillDto skill;
    private DesignationDto designation;
    private LocationDto location;
    private TaskListDto task;
    private BigDecimal ratePerHour;
    private BigDecimal amount;
    private BigDecimal hours;
    private String comment;
}
