package us.redshift.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import us.redshift.auth.domain.Role;
import us.redshift.auth.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    Optional<User> findByEmployeeId(Long employeeId);

    Optional<User> findByUserNameOrEmail(String userName, String email);

    List<User> findByIdIn(List<Long> userIds);

    List<User> findByRole(Role role);

    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE auth_users SET status=?2 WHERE employee_id in ?1", nativeQuery = true)
    int setUserStatus(List<Long> empIds, Boolean status);
}
