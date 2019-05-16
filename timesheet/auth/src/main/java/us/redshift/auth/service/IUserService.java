package us.redshift.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import us.redshift.auth.domain.User;
import us.redshift.auth.dto.LoginDto;

public interface IUserService {
     User createUser(User user);

    User loadUserByEmployeeId(Long id);

    Object authenticateUser(LoginDto login);
}
