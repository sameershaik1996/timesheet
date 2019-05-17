package us.redshift.timesheet.dto.timesheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetBasicListDto extends BaseDto {
    private Long id;
    private String name;
    private String comment;
    private TimeSheetStatus status;
    private EmployeeListDto employee;
    private Date fromDate;
    private Date toDate;
    private Integer weekNumber;
    private Long year;
    private BigDecimal billableHours;
    private BigDecimal nonBillableHours;

}
