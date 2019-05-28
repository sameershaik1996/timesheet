package us.redshift.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.auth.domain.Permission;
import us.redshift.auth.service.IPermissionService;

import java.util.Set;

@RestController
@RequestMapping("/auth/v1/api/permission")
public class PermissionController {


    @Autowired
    IPermissionService permissionService;

    @PostMapping("save")
    public ResponseEntity<?> createPermission(@RequestBody Set<Permission> permission){

        return new ResponseEntity<>(permissionService.createPermissionByList(permission), HttpStatus.CREATED);

    }

    @GetMapping("get")
    public ResponseEntity<?> getPermission(){

        return new ResponseEntity<>(permissionService.getAllPermission(), HttpStatus.OK);

    }


}
