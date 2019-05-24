package us.redshift.filter;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            UserDetails ud=new UserDetails();
                if(request.getHeader("userName")!=null&&request.getHeader("employeeId")!=null&&request.getHeader("roleName")!=null&&request.getHeader("emailId")!=null){

                    ud.setUserName(request.getHeader("userName"));
                    ud.setEmployeeId(Long.parseLong(request.getHeader("employeeId")));
                    ud.setRoleName(request.getHeader("roleName"));
                    ud.setEmail(request.getHeader("emailId"));
                }
                else{
                    throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
                }
                request.setAttribute("userDetails",ud);
            request.setAttribute("test","test");

        } catch (HttpClientErrorException ex) {
           throw ex;

        }

        filterChain.doFilter(request, response);
    }


}
