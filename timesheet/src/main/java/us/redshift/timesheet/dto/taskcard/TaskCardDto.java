package us.redshift.timesheet.dto.taskcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.*;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.dto.task.TaskListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetListDto;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardDto extends BaseDto {

    private Long id;
    private TimeSheetStatus status;
    private EmployeeListDto approvedBy;
    @NonNull
    private TaskType type;
    @NonNull
    private EmployeeListDto employee;
    @NonNull
    private SkillDto skill;
    private DesignationDto designation;
    @NonNull
    private LocationDto location;
    private BigDecimal ratePerHour;
    private BigDecimal amount;
    private BigDecimal hours;
    private String comment;
    @NonNull
    private TaskListDto task;
    @NonNull
    private ProjectListDto project;
    @NonNull
    @JsonIgnoreProperties("taskCard")
    private Set<TaskCardDetailDto> taskCardDetails;
    private TimeSheetListDto timeSheet;
}
