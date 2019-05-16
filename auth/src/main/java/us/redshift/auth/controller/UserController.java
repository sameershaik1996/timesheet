package us.redshift.auth.controller;


import org.modelmapper.ModelMapper;
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
import us.redshift.auth.domain.Permission;
import us.redshift.auth.domain.Role;
import us.redshift.auth.domain.RoleName;
import us.redshift.auth.domain.User;
import us.redshift.auth.dto.JwtAuthenticationResponse;
import us.redshift.auth.dto.LoginDto;
import us.redshift.auth.dto.UserDto;
import us.redshift.auth.security.CurrentUser;
import us.redshift.auth.security.CustomUserDetailsService;
import us.redshift.auth.security.JwtTokenProvider;
import us.redshift.auth.security.UserPrincipal;
import us.redshift.auth.service.IUserService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("auth/v1/api/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

   @Autowired
    ModelMapper modelMapper;

    @Autowired
    private IUserService userService;

    @PostMapping("save")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, HttpServletRequest servletRequest)
    {
        if(user.getRole()==null){
            Role role=new Role();
            Long id=new Long(3);
            role.setId(id);
            user.setRole(role);
        }
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, HttpServletRequest servletRequest)
    {
        System.out.println(servletRequest.getHeader("Authorization"));
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.CREATED);
    }

    @GetMapping("get/{employeeId}")
    public ResponseEntity<?> getUserByEmpId( @PathVariable Long employeeId)
    {
        UserDto userDto=modelMapper.map(userService.loadUserByEmployeeId(employeeId),UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto login)
    {
        try{System.out.println("login");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUserNameOrEmail(),login.getPassword()
                )
        );


        String jwt = tokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new JwtAuthenticationResponse(jwt),HttpStatus.OK);}
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }

       // return jwt;
    }


    @GetMapping("validatetoken")
    public ResponseEntity<?> validateToken(@CurrentUser UserPrincipal userPrincipal)
    {
        try {
            Long employeeId = userPrincipal.getEmployyeId();
            System.out.println("validate:"+employeeId);
            UserDto userDto = modelMapper.map(userService.loadUserByEmployeeId(employeeId), UserDto.class);
            return ResponseEntity.ok(userDto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(e.getMessage());
        }

        // return jwt;
    }

}
