package us.redshift.zuul.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import us.redshift.zuul.exception.CustomException;
import us.redshift.zuul.model.UserDetails;

import javax.validation.Valid;

@FeignClient(name = "auth-service",fallback = AuthFallBack.class)
public interface AuthClient {

    @GetMapping("/auth/v1/api/user/validatetoken")
    ResponseEntity<UserDetails> validateToken();
}
@Component
class AuthFallBack implements AuthClient {


    @Override
    public ResponseEntity<UserDetails> validateToken() throws CustomException, HttpClientErrorException {

        //System.out.println.println("inside");
        try {


        }catch (CustomException e){

            e.printStackTrace();
            throw e;
        }
      return new ResponseEntity<>(new UserDetails(),HttpStatus.UNAUTHORIZED);
    }
}

