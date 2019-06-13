package us.redshift.timesheet.dto.common;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeListDto extends BaseDto {

    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private DesignationDto designation;
    private Set<SkillDto> skills;
    private EmployeeListDto reportingManager;
}
