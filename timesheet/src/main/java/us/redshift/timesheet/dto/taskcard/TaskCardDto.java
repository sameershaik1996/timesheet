package us.redshift.timesheet.dto.taskcard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.EmployeeRole;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.*;
import us.redshift.timesheet.dto.project.ProjectListDto;
import us.redshift.timesheet.dto.task.TaskListDto;
import us.redshift.timesheet.dto.timesheet.TimeSheetListDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardDto extends BaseDto {

    private Long id;
    private TimeSheetStatus status = TimeSheetStatus.PENDING;
    private EmployeeListDto approvedBy;
    @NotNull(message = "taskType cannot be empty")
    private TaskType type;
    @NotNull(message = "employee cannot be empty")
    private EmployeeListDto employee;
    @NotNull(message = "skill cannot be empty")
    private SkillDto skill;
    private EmployeeRole role;
    private DesignationDto designation;
    @NotNull(message = "location cannot be empty")
    private LocationDto location;
    private BigDecimal ratePerHour = BigDecimal.valueOf(0);
    private BigDecimal amount = BigDecimal.valueOf(0);
    private BigDecimal hours = BigDecimal.valueOf(0);
    private String comment = "";
    @Pattern(regexp = "BELK_",message = "Defect Id should be of pattern BELK_[0-9]")
    private String defectId;
    @NotNull(message = "task cannot be empty")
    private TaskListDto task;
    @NotNull(message = "project cannot be empty")
    private ProjectListDto project;
    @NotNull(message = "taskCardDetails cannot be empty")
    @JsonIgnoreProperties("taskCard")
    private List<TaskCardDetailDto> taskCardDetails;
    private TimeSheetListDto timeSheet;
}
