package us.redshift.employee.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import us.redshift.employee.domain.common.Address;
import us.redshift.employee.domain.common.EmployeeStatus;
import us.redshift.employee.domain.common.Gender;
import us.redshift.employee.domain.common.MaritalStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "emp_employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "designation")

public class Employee extends BaseEntity implements Serializable {

    @Column(name = "first_name", nullable = false)
    @Size(max = 40)
    private String firstName;


    @Column(name = "last_name", nullable = false)
    @Size(max = 40)
    private String lastName;

    @JsonProperty("employeeCode")
    @Column(nullable = false, unique = true, updatable = false)
    private String employeeId;

    @Column(name = "dob", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date anniversaryDate;

    @Email
    @Column(name = "email_id", nullable = false, unique = true)
    @Size(max = 40)
    private String emailId;


    @Column(name = "phone_number", nullable = false, unique = true)
    @Size(max = 20)
    private String phoneNumber;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    // @JsonProperty("aadhar_ssn_number")
    //@Column(nullable=true,unique = true)
    //private String aadharSsnNumber;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "designation_id")
    @JsonIgnoreProperties(value = "employees")
    private Designation designation;


    @ManyToOne
    @JoinColumn(name = "reporting_manager_id")
    @JsonIgnoreProperties(value = {"designation", "skills", "address"})
    private Employee reportingManager;


    @Column(name = "joining_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    @Column(name = "resignation_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date resignationDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date lastWorkingDate;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    @JsonIgnoreProperties(value = "employees")
    @ManyToMany()
    @JoinTable(name = "emp_employees_skill",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();


//    @ManyToMany(targetEntity = Skill.class, mappedBy = "employees")
//    private Set<Skill> skills = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

}