package us.redshift.timesheet.domain;

import lombok.*;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.task.Task;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "pss_employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee extends BaseEntity {

    private String firstName;

    private String lastName;

    private EmployeeRole role;

    private BigDecimal rate;

    private Project project;

    private Task task;


}
