package us.redshift.zuul.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {

    private Long id;
    private RoleName name;

    private Set<Permission> permissions = new HashSet<>();



}
