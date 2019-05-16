package us.redshift.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.auth.domain.Role;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimsDto {

    private Long id;

    @JsonProperty("username")
    private String userName;


    private Long employeeId;


    private Set<Role> roles = new HashSet<>();
}
