package us.redshift.auth.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "auth_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends BaseEntity implements Serializable {

        @NotBlank
        @Size(max = 15)
        @Column(unique = true)
        @JsonProperty("username")
        private String userName;

        @NaturalId(mutable = false)
        @NotBlank
        @Size(max = 40)
        @Email
        @Column(unique = true)
        private String email;

        @NotNull
        @JsonProperty("employee_id")
        @Column(unique = true)
        private Long employeeId;

        @NotBlank
        @Size(max = 100)
        private String password;

        @ManyToMany(fetch = FetchType.EAGER)
        @JsonIgnoreProperties(value = "roles")
        @JoinTable(name = "auth_employee_roles",
                joinColumns = @JoinColumn(name = "employee_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();


}
