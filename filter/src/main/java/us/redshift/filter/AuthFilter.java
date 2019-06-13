package us.redshift.filter;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import us.redshift.filter.model.UserDetails;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import java.io.IOException;


@NoArgsConstructor
public class AuthFilter extends OncePerRequestFilter {



    private RestTemplate restTemplate;

    private String authUrl;

    public AuthFilter(RestTemplate restTemplate, String authUrl) {
        this.restTemplate=restTemplate;
        this.authUrl=authUrl;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException ,HttpClientErrorException{
        try {
            UserDetails ud=new UserDetails();
                if(request.getHeader("userName")!=null&&request.getHeader("employeeId")!=null&&request.getHeader("roleName")!=null&&request.getHeader("emailId")!=null){

                    ud.setUserName(request.getHeader("userName"));
                    ud.setEmployeeId(Long.parseLong(request.getHeader("employeeId")));
                    ud.setRoleName(request.getHeader("roleName"));
                    ud.setEmail(request.getHeader("emailId"));
                }
                else if(!request.getHeader("Authorization").isEmpty()){
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", request.getHeader("Authorization"));
                    HttpEntity entity = new HttpEntity(headers);
                    //System.out.println(restTemplate+" "+authUrl);
                    ResponseEntity<UserDetails> res = restTemplate
                            .exchange(authUrl+"/v1/api/user/validatetoken", HttpMethod.GET, entity, UserDetails.class);
                    ud=res.getBody();
                }
                else{
                    throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
                }
                request.setAttribute("userDetails",ud);
            request.setAttribute("test","test");

        } catch (Exception ex) {
            //ex.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);

        }

        filterChain.doFilter(request, response);
    }


}
