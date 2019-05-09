package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListDto extends BaseDto {

    private String employeeCode;
    private String firstName;
    private String lastName;
    private DesignationDto designation;
    private List<SkillDto> skills;
}
