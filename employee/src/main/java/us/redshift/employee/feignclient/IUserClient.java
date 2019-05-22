package us.redshift.employee.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import us.redshift.employee.dto.SignUpDto;

import javax.validation.Valid;

@FeignClient(name = "auth-service",url = "${zuul.auth.path}")
public interface IUserClient {

    @PostMapping("/v1/api/user/save")
    void createUser(@Valid @RequestBody SignUpDto user, @RequestParam Long roleId);

}
