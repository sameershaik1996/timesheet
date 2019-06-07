package us.redshift.timesheet.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.Address;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto extends BaseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String employeeCode;
    private Date dob;
    private String emailId;
    private String phoneNumber;
    private String gender;
    private String maritalStatus;
    private DesignationDto designation;
    @JsonIgnoreProperties("reportingManager")
    private EmployeeListDto reportingManager;
    private Date joiningDate;
    private Date anniversaryDate;
    private Date resignationDate;
    private String status;
    private Set<SkillDto> skills;
    private Address address;


    public EmployeeDto(Long id, String firstName, String lastName, String employeeCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeCode = employeeCode;
    }
}
