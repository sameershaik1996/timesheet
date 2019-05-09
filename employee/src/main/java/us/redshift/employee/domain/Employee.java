package us.redshift.employee.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.employee.domain.common.Address;
import us.redshift.employee.domain.common.Gender;
import us.redshift.employee.domain.common.MaritalStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="emp_employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "designation")

public class Employee extends BaseEntity implements Serializable {

    @Column(name="first_name",nullable = false)
    @Size(max = 40)
    private String firstName;


    @Column(name="last_name",nullable = false)
    @Size(max = 40)
    private String lastName;


    @Column(nullable =false,unique = true,updatable = false)
    private String employeeId;

    @Column(name="dob",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date anniversaryDate;

    @Email
    @Column(name="email_id",nullable = false,unique = true)
    @Size(max = 40)
    private String emailId;


    @Column(name="phone_number",nullable = false,unique = true)
    @Size(max = 20)
    private String phoneNumber;

    @Column(name="gender",nullable=false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="marital_status",nullable=false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

   // @JsonProperty("aadhar_ssn_number")
    //@Column(nullable=true,unique = true)
    //private String aadharSsnNumber;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="designation_id")
    @JsonIgnoreProperties(value = "employees")
    private Designation designation;


    @Column(name="reporting_Manager",nullable=true)
    private Long reportingManager;



    @Column(name="joining_date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    @Column(name="resignation_date",nullable = true)
    @Temporal(TemporalType.DATE)
    private Date resignationDate;

    @Column(nullable = false)
    private Boolean status=Boolean.TRUE;

    @JsonIgnoreProperties(value="employees")
    @ManyToMany
    @JoinTable(name = "emp_employees_skill",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

}