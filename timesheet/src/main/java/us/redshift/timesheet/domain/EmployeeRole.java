package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;

@Entity
@Table(name = "pss_employee_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    @JsonIgnoreProperties(value = "createdBy", allowGetters = true)
    private String createdBy;


    @LastModifiedBy
    @Column(nullable = false)
    @JsonIgnoreProperties(value = "updatedBy", allowGetters = true)
    private String updatedBy;

    @Column(nullable = false, unique = true)
    private String role;
}
