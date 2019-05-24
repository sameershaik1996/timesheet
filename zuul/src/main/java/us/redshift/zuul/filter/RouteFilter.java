package us.redshift.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import us.redshift.zuul.model.UserDetails;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RouteFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "route";
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

        System.out.println("Inside route Filter");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();
        UserDetails ud=(UserDetails) request.getAttribute("userDetails");
        if(ud!=null) {
            System.out.println(ud.getRole().getName().toString());
            requestContext.addZuulRequestHeader("userName",ud.getUserName());
            requestContext.addZuulRequestHeader("employeeId",ud.getEmployeeId().toString());
            requestContext.addZuulRequestHeader("roleName",ud.getRole().getName().toString());
            requestContext.addZuulRequestHeader("emailId",ud.getEmail());
        }

        return requestContext;
    }
}
