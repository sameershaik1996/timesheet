package us.redshift.timesheet.dto.timesheet;

import lombok.*;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetListDto extends BaseDto {
    @NonNull
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
}
