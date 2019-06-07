package us.redshift.timesheet.domain;

import lombok.*;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pss_employee_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRole extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String role;
}
