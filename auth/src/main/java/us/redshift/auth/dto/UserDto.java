package us.redshift.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.auth.domain.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {

    @JsonProperty("username")
    private String userName;

    private String email;

    private Long employeeId;

    private Role role;
}
