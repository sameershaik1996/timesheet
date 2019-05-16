package us.redshift.auth.service;

import us.redshift.auth.domain.Permission;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    List<Permission> createPermissionByList(Set<Permission> permission);
    List<Permission> getAllPermission();
}
