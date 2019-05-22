package us.redshift.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import us.redshift.employee.domain.Designation;
import us.redshift.employee.domain.Skill;
import us.redshift.employee.domain.common.Address;
import us.redshift.employee.domain.common.Gender;
import us.redshift.employee.domain.common.MaritalStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "designation")
public class EmployeeDto extends BaseDto implements Serializable {


    private String firstName;


    private String lastName;

    private String employeeId;

    private Date dob;

    private Date anniversaryDate;

    private String emailId;


    private String phoneNumber;

    private Gender gender;

    private MaritalStatus maritalStatus;



    private Designation designation;

    private Long reportingManager;




    private Date joiningDate;


    private Date resignationDate;


    private Boolean status=Boolean.TRUE;

    private Set<Skill> skills = new HashSet<>();

    private Long roleId;

    private Address address;


}
