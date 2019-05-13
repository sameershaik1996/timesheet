package us.redshift.timesheet.dto.taskcard;

import lombok.*;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.CommonDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.dto.task.TaskListDto;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardListDto extends BaseDto {

    private Long id;
    private TimeSheetStatus status;
    @NonNull
    private TaskType type;
    @NonNull
    private EmployeeListDto employee;
    @NonNull
    private SkillDto skill;
    @NonNull
    private CommonDto location;
    private BigDecimal billableHours;
    private BigDecimal nonBillableHours;
    @NonNull
    private TaskListDto task;
    @NonNull
    private ProjectListDto project;
}
