package us.redshift.auth.security;

import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import us.redshift.auth.domain.User;
import us.redshift.auth.exception.AuthException;
import us.redshift.auth.exception.CustomException;
import us.redshift.auth.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;


    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try {
            String jwt = getJwtFromRequest(request);

            logger.info(request.getHeader("RequestTo"));
            logger.info(request.getRequestURI());
            logger.info(jwt);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                System.out.println("inside");
                Long empId= tokenProvider.getUserDetails(jwt);
                User user=userService.loadUserByEmployeeId(empId);
                if(user.getStatus()!=true){
                    throw new CustomException("you have been de-activated");
                }
                UserDetails userDetails = UserPrincipal.create(user);

                request.setAttribute("userDetails",user);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (CustomException ex) {
            entryPoint.commence(request, response,  new AuthException(ex.getMessage()));
           // response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
             //       "adasdas");
            logger.error("Could not set user authentication in security context", ex.getMessage());
            return;
            //throw new CustomException(ex.getMessage());
            //throw new ServletException(ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}