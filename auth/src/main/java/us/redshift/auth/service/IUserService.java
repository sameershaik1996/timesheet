package us.redshift.auth.service;

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


    int checkIfUserIsActive(String userNameOrEmail);

    int updateUserStatus(List<Long> empIds, Boolean status);

    User updateUserStatusAndRole(User currentUser);
}
