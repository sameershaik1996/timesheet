package us.redshift.employee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="emp_designations")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "employees")
@AllArgsConstructor
public class Designation extends BaseEntity implements Serializable {


    @Column(name="designation",nullable = false,unique = true)
    @Size(max = 40)
    private String designation;
    @JsonIgnore
    @OneToMany(mappedBy = "designation",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    @JsonIgnoreProperties(value = "designation")
    private Set<Employee> employees=new HashSet<>();

}
