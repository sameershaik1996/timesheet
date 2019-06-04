package us.redshift.employee.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.employee.dto.SignUpDto;
import us.redshift.filter.model.Role;
import us.redshift.filter.model.UserDetails;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "auth-service",url = "${zuul.auth.path}")
public interface IUserClient {

    @PostMapping("/v1/api/user/save")
    UserDetails createUser(@Valid @RequestBody SignUpDto user, @RequestParam Long roleId,@RequestParam Boolean status);

    @GetMapping("/v1/api/user/get")
    List<UserDetails> getUser(@RequestParam String roleName);

    @GetMapping("/v1/api/user/get/{id}")
    UserDetails getUser(@PathVariable Long id);

    @PutMapping("/v1/api/user/update/role/{employeeId}")
    Void updateUser(@PathVariable Long employeeId, @RequestParam(value = "status")Boolean status,@RequestBody  Role role);

    @PutMapping("/v1/api/user/update/status")
    Void updateUser( @RequestParam(value = "empId")List<Long> empId,@RequestParam(value = "status")Boolean status);

}
