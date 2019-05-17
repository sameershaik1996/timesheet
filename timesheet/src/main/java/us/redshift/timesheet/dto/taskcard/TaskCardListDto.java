package us.redshift.timesheet.dto.taskcard;

import lombok.*;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.LocationDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.task.TaskListDto;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardListDto extends BaseDto {

    private Long id;
    private TimeSheetStatus status;
    private EmployeeListDto approvedBy;
    @NonNull
    private TaskType type;
    @NonNull
    private EmployeeListDto employee;
    @NonNull
    private SkillDto skill;
    @NonNull
    private LocationDto location;
    private TaskListDto task;
    private BigDecimal ratePerHour;
    private BigDecimal amount;
    private BigDecimal hours;
    private String comment;
}
