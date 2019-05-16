package us.redshift.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import us.redshift.auth.domain.Permission;
import us.redshift.auth.domain.Role;
import us.redshift.auth.domain.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class JwtGrantFilter extends OncePerRequestFilter {
    @Autowired
    JwtAuthenticationEntryPoint entryPoint;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException,ResponseStatusException {

    try {
        System.out.println("grant filter");

        User ud = (User) httpServletRequest.getAttribute("userDetails");
        String requestUri = httpServletRequest.getHeader("RequestTo");
        if(requestUri==null)
            requestUri = httpServletRequest.getRequestURI();
        if (ud != null && requestUri != null) {

            String permissionFromUri = getPermission((requestUri.substring(requestUri.lastIndexOf("api") + 4)),ud.getRole().getName().toString());
            Set<Permission> permissions = ud.getRole().getPermissions();

            List<String> authorities = permissions.stream().map(permission ->
                    (permission.getName())
            ).collect(Collectors.toList());
            System.out.println(permissionFromUri);
            System.out.println(authorities);
            if (!authorities.contains(permissionFromUri)) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"you don't have enough permissions to access this resource");

            }

        }
    }
    catch (Exception ex){
        ex.printStackTrace();
    }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private String getPermission(String api,String role) {
        String[] split=api.split("/");
        StringBuilder permission=new StringBuilder();
        if(split[1].equals("save")||split[1].equals("update")){
            permission.append(split[0]+"_"+"crud");
        }
        else if(split[1].equals("get")&&!role.equals("EMPLOYEE"))
        {
            permission.append(split[0]+"_"+"crud");
        }
        else if(split[1].equals("get")&&role.equals("EMPLOYEE")){
            permission.append(split[0]+"_"+"get");
        }
        else
        {
            permission.append(split[0]+"_"+"common");
        }
        return permission.toString();

    }
}
