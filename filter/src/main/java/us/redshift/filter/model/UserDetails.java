package us.redshift.filter.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@NoArgsConstructor
public class UserDetails implements Serializable {

    @JsonProperty("username")
    private String userName;

    private String email;

    private Long employeeId;

    private String roleName;

    @Override
    public String toString() {
        return "UserDetails{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", employeeId=" + employeeId +
                ", roles=" + roleName +
                '}';
    }
}
