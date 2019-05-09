package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.Address;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto extends BaseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String name;
    private String employeeCode;
    private Date dob;
    private String emailId;
    private String phoneNumber;
    private String gender;
    private String maritalStatus;
    private DesignationDto designation;
    private Long reportingManager;
    private Date joiningDate;
    private Date resignationDate;
    private Boolean status = Boolean.TRUE;
    private List<SkillDto> skills = new ArrayList<>();
    private Address address;


    public EmployeeDto(Long id, String firstName, String lastName, String employeeCode, String name) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeCode = employeeCode;
        this.name = name;
    }
}
