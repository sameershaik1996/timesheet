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

    private Long id;
    private String taskCode;
    private String name;
    private TaskType type;
    private TaskStatus status;
    private CommonDto project;
    private CommonDto client;
}
