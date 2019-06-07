package us.redshift.auth.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import us.redshift.auth.exception.ApiError;
import us.redshift.auth.exception.CustomException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter  {
   /* private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        int flag=1;
        try {
            filterChain.doFilter(request, response);
        }
        catch (ServletException e) {
            log.error(e.getMessage());
            ApiError apiError=new ApiError(HttpStatus.UNAUTHORIZED,e.getMessage(),e);

            response.getWriter().write(convertObjectToJson(apiError));
            flag=0;
            // throw new ServletException(e.getMessage());

        }
        if(flag==0){
            filterChain.doFilter(request, response);
        }

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }*/
}