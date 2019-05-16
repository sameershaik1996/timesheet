package us.redshift.auth.service;

import us.redshift.auth.domain.Role;

public interface IRoleService {
    Role createRole(Role role);

    Role updateRole(Role role);
}
