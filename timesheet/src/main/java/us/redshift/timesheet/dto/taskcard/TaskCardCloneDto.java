package us.redshift.timesheet.dto.taskcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.LocationDto;
import us.redshift.timesheet.dto.common.SkillDto;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.dto.task.TaskListDto;

import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardCloneDto {

    private TimeSheetStatus status = TimeSheetStatus.PENDING;

    @NonNull
    private TaskType type;
    @NonNull
    private EmployeeListDto employee;
    @NonNull
    private SkillDto skill;
    @NonNull
    private LocationDto location;
    private BigDecimal ratePerHour;
    private BigDecimal amount;
    private BigDecimal hours;

    @NonNull
    private TaskListDto task;
    @NonNull
    private ProjectListDto project;
    @NonNull
    @JsonIgnoreProperties("taskCard")
    private Set<TaskCardDetailCloneDto> taskCardDetails;


}
