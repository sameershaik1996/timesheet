package us.redshift.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.auth.domain.Permission;
import us.redshift.auth.repository.PermissionRepository;

import java.util.List;
import java.util.Set;

@Service
public class PermissionService implements IPermissionService {
    @Autowired
    PermissionRepository permissionRepository;
    @Override
    public List<Permission> createPermissionByList(Set<Permission> permission) {
        return permissionRepository.saveAll(permission);
    }

    public List<Permission> getAllPermission() {
        return permissionRepository.findAll();
    }


}
