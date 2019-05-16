package us.redshift.timesheet.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.task.TaskStatus;
import us.redshift.timesheet.domain.taskcard.TaskType;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.CommonDto;

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
