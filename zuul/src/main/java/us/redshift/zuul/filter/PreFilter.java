package us.redshift.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import us.redshift.zuul.client.AuthClient;
import us.redshift.zuul.model.UserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class PreFilter extends ZuulFilter {

    @Autowired
    AuthClient authClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("Inside pre Filter");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();
        try{

            if(request.getRequestURI().contains("v1/api")&&!request.getRequestURI().contains("/auth")) {
                UserDetails ud = authClient.validateToken();
                request.setAttribute("userDetails",ud);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            ZuulException zuulException = new ZuulException("unauthorized", HttpStatus.UNAUTHORIZED.value(), "unauthorized access");
            throw new ZuulRuntimeException(zuulException);
        }

        return null;
    }
}