package us.redshift.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.redshift.auth.domain.Role;
import us.redshift.auth.domain.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);


}