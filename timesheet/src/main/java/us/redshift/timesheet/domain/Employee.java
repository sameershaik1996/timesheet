package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.task.Task;

import javax.persistence.*;


@Entity
@Table(name = "pss_employees",
        uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "role_id", "task_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee extends BaseEntity {

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    private String firstName;

    private String lastName;

    private String employeeCode;

    private Long designationId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private EmployeeRole role;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnoreProperties(value = {"employees"}, allowSetters = true)
    private Task task;


}
