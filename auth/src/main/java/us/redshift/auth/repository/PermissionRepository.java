package us.redshift.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.auth.domain.Permission;

public interface PermissionRepository extends JpaRepository<Permission,Long> {


}
