package us.redshift.timesheet.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.Employee;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.CommonDto;
import us.redshift.timesheet.dto.common.SkillDto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto extends BaseDto {

    private Long id;
    @NotNull(message = "taskCode cannot be empty")
    private String taskCode;
    @NotNull(message = "name cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "taskType cannot be empty")
    private TaskType type;
    private TaskStatus status = TaskStatus.ACTIVE;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    private BigDecimal usedHour = BigDecimal.valueOf(0);
    private BigDecimal billableHour = BigDecimal.valueOf(0);
    private BigDecimal nonBillableHour = BigDecimal.valueOf(0);
    @NotNull(message = "skills cannot be empty")
    private List<SkillDto> skills;
    @NotNull(message = "employees cannot be empty")
    private List<Employee> employees;
    @NotNull(message = "project cannot be empty")
    private CommonDto project;


}
