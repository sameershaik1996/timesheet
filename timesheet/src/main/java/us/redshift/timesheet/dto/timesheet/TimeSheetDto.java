package us.redshift.timesheet.dto.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.taskcard.TaskCardDto;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetDto extends BaseDto {
    private Long id;
    private String name;
    private String comment;
    private TimeSheetStatus status;
    private EmployeeListDto approvedBy;
    private EmployeeListDto employee;
    private Date fromDate;
    private Date toDate;
    private Integer weekNumber;
    private Long year;
    @JsonIgnoreProperties("timeSheet")
    private Set<TaskCardDto> taskCards;
    @JsonIgnoreProperties("timeSheet")
    private Set<TimeOffDto> timeOffs;
}
