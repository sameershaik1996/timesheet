package us.redshift.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import us.redshift.employee.domain.Designation;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.Skill;
import us.redshift.employee.domain.common.Address;
import us.redshift.employee.domain.common.EmployeeStatus;
import us.redshift.employee.domain.common.Gender;
import us.redshift.employee.domain.common.MaritalStatus;
import us.redshift.filter.model.Role;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "FirstName is Required")
    private String firstName;

    @NotNull(message = "LastName is Required")
    private String lastName;
    @JsonProperty("employeeCode")
    private String employeeId;

    @NotNull(message = "DOB is Required")
    private Date dob;


    private Date lastWorkingDate;

    @NotNull(message = "EmailID is Required")
    private String emailId;

    @NotNull(message = "PhoneNumber is Required")
    @Size(min = 10,max = 10,message = "Can't exceed more than 10 digits")
    private String phoneNumber;

    @NotNull(message = "Gender is Required")
    private Gender gender;

    private MaritalStatus maritalStatus;



    private Designation designation;


    @JsonIgnoreProperties(value = {"designation","skills","address"})
    private Employee reportingManager;




    private Date joiningDate;


    private Date resignationDate;


    private EmployeeStatus status=EmployeeStatus.ACTIVE;


    @JsonIgnoreProperties(value="employees")
    private Set<Skill> skills = new HashSet<>();

    @NotNull(message = "Role is Required")
    private Role role;
    @NotNull(message = "Address is Required")
    private Address address;


}
