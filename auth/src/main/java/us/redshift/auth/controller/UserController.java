package us.redshift.auth.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import us.redshift.auth.domain.Role;
import us.redshift.auth.domain.RoleName;
import us.redshift.auth.domain.User;
import us.redshift.auth.dto.JwtAuthenticationResponse;
import us.redshift.auth.dto.LoginDto;
import us.redshift.auth.dto.UserDto;
import us.redshift.auth.exception.BadRequestException;
import us.redshift.auth.repository.RoleRepository;
import us.redshift.auth.security.CurrentUser;
import us.redshift.auth.security.JwtTokenProvider;
import us.redshift.auth.security.UserPrincipal;
import us.redshift.auth.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("auth/v1/api/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    RoleRepository roleRepository;

   @Autowired
    ModelMapper modelMapper;

    @Autowired
    private IUserService userService;

    @PostMapping("save")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, @RequestParam(value = "roleId",required = false)Long roleId,HttpServletRequest servletRequest)
    {
        if(user.getRole()==null){
            Role role=new Role();
            Long id=new Long(roleId);
            role.setId(id);
            user.setRole(role);
        }
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, HttpServletRequest servletRequest)
    {

        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.CREATED);
    }


    @PutMapping("/update/role/{employeeId}")
    public ResponseEntity<?> updateUsersRole(@PathVariable Long employeeId,@RequestBody Role role)
    {
        User currentUser=userService.loadUserByEmployeeId(employeeId);
        currentUser.setRole(role);
        return new ResponseEntity<>(userService.updateUser(currentUser), HttpStatus.CREATED);
    }

    @GetMapping("get/{employeeId}")
    public ResponseEntity<?> getUserByEmpId( @PathVariable Long employeeId)
    {
        UserDto userDto=modelMapper.map(userService.loadUserByEmployeeId(employeeId),UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }



    @GetMapping("get")
    public ResponseEntity<?> getUserByRole( @RequestParam(value = "roleName",required = false) String roleName)
    {
        List<UserDto> userDto=new ArrayList<>();
        if(roleName!=null) {
             Role role= roleRepository.findByName(RoleName.get(roleName.toUpperCase()));
             if(role!=null)
                userService.findUserByRole(role).forEach(user -> userDto.add(modelMapper.map(user,UserDto.class)));
             else
                 return new ResponseEntity<>(new BadRequestException("Role doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        else
        {
            userService.findAllUsers().forEach(user -> userDto.add(modelMapper.map(user,UserDto.class)));
        }

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto login)throws BadCredentialsException
    {
        System.out.println("login");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUserNameOrEmail(),login.getPassword()
                )
        );


        String jwt = tokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new JwtAuthenticationResponse(jwt),HttpStatus.OK);
    }


       // return jwt;



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
