package us.redshift.zuul.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import us.redshift.zuul.model.UserDetails;

import javax.validation.Valid;

@FeignClient(name = "auth-service",fallback = AuthFallBack.class)
public interface AuthClient {

    @GetMapping("/auth/v1/api/user/validatetoken")
    UserDetails validateToken();
}
    @Component
    class AuthFallBack implements AuthClient {


        @Override
        public UserDetails validateToken() {
            return new UserDetails();
        }
    }

