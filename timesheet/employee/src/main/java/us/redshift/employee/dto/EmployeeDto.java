package us.redshift.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import us.redshift.employee.domain.common.Gender;
import us.redshift.employee.domain.common.MaritalStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "designation")
public class EmployeeDto extends BaseDto implements Serializable {

    private String firstName;

    private String lastName;

    private Date dob;

    private String emailId;

    private String phoneNumber;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private DesignationDto designation;

    private Long approverId;

    private Long leaveApproverId;

    private Date joiningDate;

    private Date resignationDate;

    private List<SkillsDto> skills;
    @JsonIgnore
    private SignUpDto signUp;

}
