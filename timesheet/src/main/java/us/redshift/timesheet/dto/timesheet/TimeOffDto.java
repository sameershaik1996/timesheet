package us.redshift.timesheet.dto.timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.TimeOffReasons;
import us.redshift.timesheet.domain.timesheet.TimeOffDate;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeOffDto  {
    private Long id;
    private Long employeeId;
    private TimeOffReasons reason;
    private TimeSheetStatus status=TimeSheetStatus.PENDING;
    private List<TimeOffDate> dates;
    private BigDecimal hours;
    private TimeSheetListDto timeSheet;

}
