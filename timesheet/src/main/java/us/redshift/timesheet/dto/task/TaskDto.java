package us.redshift.timesheet.dto.task;

import lombok.*;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.CommonDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.common.SkillDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto extends BaseDto {

    private Long id;
    @NonNull
    private String taskCode;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private TaskType type;
    @NonNull
    private TaskStatus status;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    private BigDecimal usedHour;
    private BigDecimal billableHour;

    private BigDecimal nonBillableHour;
    @NonNull
    private List<SkillDto> skills;
    @NonNull
    private List<EmployeeListDto> employees;
    @NonNull
    private CommonDto project;


}
