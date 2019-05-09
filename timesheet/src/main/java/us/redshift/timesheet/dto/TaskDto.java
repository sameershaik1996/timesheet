package us.redshift.timesheet.dto;

import lombok.*;
import us.redshift.timesheet.domain.TaskStatus;
import us.redshift.timesheet.domain.TaskType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto extends BaseDto {

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

    private BigDecimal billableHour;

    private BigDecimal nonBillableHour;
    @NonNull
    private Set<SkillDto> skills;
    @NonNull
    private Set<EmployeeListDto> employees;
    @NonNull
    private CommonDto project;


}
