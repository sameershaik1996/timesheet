package us.redshift.auth.domain;


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
        @NaturalId
        private Long employeeId;

        @NotBlank
        @JsonIgnoreProperties(allowGetters = false,allowSetters = true)
        @Size(max = 100)
        private String password;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name="role_id")
        private Role role;

        private Boolean status=Boolean.TRUE;

        @Override
        public String toString() {
                return "User{" +
                        "userName='" + userName + '\'' +
                        ", email='" + email + '\'' +
                        ", employeeId=" + employeeId +
                        ", password='" + password + '\'' +
                        ", roles=" + role +
                        '}';
        }
}
