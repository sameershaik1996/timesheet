package us.redshift.auth.service;

import us.redshift.auth.domain.Role;

import java.util.List;

public interface IRoleService {
    Role createRole(Role role);

    Role updateRole(Role role);

    List<Role> getAllRoles();
}
