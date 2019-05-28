package us.redshift.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import us.redshift.employee.domain.Designation;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.Skill;
import us.redshift.employee.domain.common.Address;
import us.redshift.employee.domain.common.Gender;
import us.redshift.employee.domain.common.MaritalStatus;
import us.redshift.filter.model.Role;


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
    @JsonProperty("employeeCode")
    private String employeeId;

    private Date dob;

    private Date anniversaryDate;

    private String emailId;


    private String phoneNumber;

    private Gender gender;

    private MaritalStatus maritalStatus;



    private Designation designation;


    @JsonIgnoreProperties(value = {"designation","skills","address"})
    private Employee reportingManager;




    private Date joiningDate;


    private Date resignationDate;


    private Boolean status=Boolean.TRUE;

    @JsonIgnoreProperties(value="employees")
    private Set<Skill> skills = new HashSet<>();

    private Role role;

    private Address address;


}
