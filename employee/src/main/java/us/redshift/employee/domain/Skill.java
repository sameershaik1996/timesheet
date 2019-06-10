package us.redshift.employee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="emp_skills")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "employees")
@AllArgsConstructor
public class Skill extends BaseEntity{

    @Column(name="skill",nullable = false,unique = true)
    private String skill;

    @JsonIgnoreProperties(value="skills")
    @JsonIgnore
    @ManyToMany(mappedBy="skills",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    private Set<Employee> employees=new HashSet<>();








}
