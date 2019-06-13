package us.redshift.zuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import us.redshift.zuul.exception.ApiError;
import us.redshift.zuul.model.UserDetails;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public Object run()   {

        //System.out.println.println("Inside route Filter");
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request=requestContext.getRequest();
        HttpServletResponse response=requestContext.getResponse();
        UserDetails ud=(UserDetails) request.getAttribute("userDetails");
        if(ud!=null) {
            ////System.out.println.println("ud:"+ud);
            if(ud.getEmail()!=null){
                //System.out.println.println(ud.getRole().getName().toString());
                requestContext.addZuulRequestHeader("userName",ud.getUserName());
                requestContext.addZuulRequestHeader("employeeId",ud.getEmployeeId().toString());
                requestContext.addZuulRequestHeader("roleName",ud.getRole().getName().toString());
                requestContext.addZuulRequestHeader("emailId",ud.getEmail());
            }
            else {
                try {
                    ApiError apiError=new ApiError(HttpStatus.UNAUTHORIZED,"invalid token or you dont have enough permissions to access this resource");
                    response.sendError(HttpStatus.UNAUTHORIZED.value(),"invalid token or you dont have enough permissions to access this resource");
                }catch (IOException e){

                }
            }
        }

        return requestContext;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
