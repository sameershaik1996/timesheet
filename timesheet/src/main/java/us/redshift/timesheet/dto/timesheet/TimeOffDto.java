package us.redshift.timesheet.dto.timesheet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeOffDto extends BaseDto {
    private Long id;
    private EmployeeListDto employee;
    private String reason;
    private Date date;
    private BigDecimal hours;
    @JsonIgnoreProperties({"timeOffs", "taskCardDetails"})
    private TimeSheetDto timeSheet;

}
