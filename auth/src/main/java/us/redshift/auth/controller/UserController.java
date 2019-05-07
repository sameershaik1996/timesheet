package us.redshift.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import us.redshift.auth.domain.User;
import us.redshift.auth.dto.LoginDto;
import us.redshift.auth.security.CurrentUser;
import us.redshift.auth.security.CustomUserDetailsService;
import us.redshift.auth.security.JwtTokenProvider;
import us.redshift.auth.security.UserPrincipal;
import us.redshift.auth.service.IUserService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("auth/v1/api/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    private IUserService userService;

    @PostMapping("save")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, HttpServletRequest servletRequest)
    {
        System.out.println(servletRequest.getHeader("Authorization"));
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("{employeeId}")
    public ResponseEntity<?> getUserByEmpId(@CurrentUser UserPrincipal up, @PathVariable Long employeeId)
    {
        return new ResponseEntity<>(userService.loadUserByEmployeeId(employeeId), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto login)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUserNameOrEmail(),login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(jwt);
       // return jwt;
    }

}
