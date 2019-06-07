package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.task.Task;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "pss_employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee extends BaseEntity {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private String employeeCode;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private EmployeeRole role;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnoreProperties(value = {"employees"}, allowSetters = true)
    private Task task;


}
