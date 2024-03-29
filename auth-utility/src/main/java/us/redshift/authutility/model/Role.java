package us.redshift.authutility.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Role {

    private Long id;
    private RoleName name;

    private Set<Permission> permissions = new HashSet<>();



}
