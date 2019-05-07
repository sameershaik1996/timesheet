package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.TaskStatus;
import us.redshift.timesheet.domain.TaskType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskListDto extends BaseDto {

    private TaskType type;
    private TaskStatus status;
    private BaseDto project;
    private BaseDto client;
}
