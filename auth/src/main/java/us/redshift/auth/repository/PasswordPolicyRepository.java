package us.redshift.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.auth.domain.PasswordPolicy;

public interface PasswordPolicyRepository extends JpaRepository<PasswordPolicy, Long> {
}
