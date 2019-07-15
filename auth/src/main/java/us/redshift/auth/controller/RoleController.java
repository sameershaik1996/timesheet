package us.redshift.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.auth.domain.Role;
import us.redshift.auth.repository.RoleRepository;
import us.redshift.auth.service.IRoleService;

import java.util.List;

@RestController
@RequestMapping("/auth/v1/api/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    IRoleService roleService;
    @PostMapping("assignpermission")
    public ResponseEntity<?> assignPermissionForRole(@RequestBody List<Role> role){

        return new ResponseEntity<>(roleRepository.saveAll(role), HttpStatus.CREATED);

    }

    @PostMapping("save")
    public ResponseEntity<?> craeteRole(@RequestBody Role role){

        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);

    }

    @PutMapping("update")
    public ResponseEntity<?> updateRole(@RequestBody Role role){

        return new ResponseEntity<>(roleService.updateRole(role), HttpStatus.CREATED);

    }

    @GetMapping("get")
    public ResponseEntity<?> getRole(){

        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);

    }

}
