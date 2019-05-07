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

    private String description;
    private TaskType type;
    private TaskStatus status;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    private BigDecimal billableHour;
    private BigDecimal nBillableHour;
    private Set<BaseDto> skills;
    private Set<BaseDto> employees;
    @NonNull
    private BaseDto project;
}
