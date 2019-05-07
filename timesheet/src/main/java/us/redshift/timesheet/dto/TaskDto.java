package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.TaskStatus;
import us.redshift.timesheet.domain.TaskType;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String name;
    private String description;
    private TaskType type = TaskType.BILLABLE;
    private TaskStatus status = TaskStatus.PENDING;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    private Set<Long> skillId;
    private Set<Long> employeeId;
    private ProjectDto project;
}
