package us.redshift.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import us.redshift.auth.domain.Role;
import us.redshift.auth.domain.User;
import us.redshift.auth.dto.LoginDto;

import java.util.List;

public interface IUserService {
     User createUser(User user);

    User loadUserByEmployeeId(Long id);

    Object authenticateUser(LoginDto login);

    User updateUser(User user);

    List<User> findUserByRole(Role role);

    List<User> findAllUsers();
}
