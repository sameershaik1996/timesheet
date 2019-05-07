package us.redshift.authutility.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import us.redshift.authutility.model.User;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@NoArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {



    private JwtTokenProvider tokenProvider;

    public  JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider)
    {
        this.tokenProvider=jwtTokenProvider;

    }
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        System.out.println(request.getContextPath());
        if(!request.getContextPath().contains("swagger-ui.html")) {
            final String token = getJwtFromRequest(request); // The part after "Bearer "
            try {
                // LOGGER.info(token);
                if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                    User user = tokenProvider.getUserDetails(token);
                    req.setAttribute("details", user);
                    //LOGGER.info(user.toString());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

            } catch (final Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        chain.doFilter(req, res);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ","");
        }
        return null;
    }
}
