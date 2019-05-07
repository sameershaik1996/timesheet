package us.redshift.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import us.redshift.auth.domain.User;
import us.redshift.auth.dto.LoginDto;
import us.redshift.auth.repository.UserRepository;
import us.redshift.auth.security.JwtTokenProvider;
import us.redshift.auth.security.UserPrincipal;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User loadUserByEmployeeId(Long id) {
        return (userRepository.findByEmployeeId(id).get());
    }

    @Override
    public Object authenticateUser(LoginDto login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUserNameOrEmail(),login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        //return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        return jwt;
    }
}
