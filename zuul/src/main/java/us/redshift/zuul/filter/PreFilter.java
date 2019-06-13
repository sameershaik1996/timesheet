package us.redshift.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import us.redshift.zuul.client.AuthClient;
import us.redshift.zuul.model.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

public class PreFilter extends ZuulFilter {

    @Autowired
    RestTemplate restTemplate;



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
        //System.out.println.println("Inside pre Filter");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();
        try{

            if(request.getRequestURI().contains("v1/api")&&!request.getRequestURI().contains("/auth")) {
                UserDetails ud= authClient.validateToken().getBody();

                if(ud!=null) {
//                    //System.out.println.println("error:"+ud);

                    request.setAttribute("userDetails", ud);

                }
               /* final HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer "+request.getHeader("Authorization"));
                headers.add("RequestTo", request.getRequestURI());
                HttpEntity<String> entity = new HttpEntity<String>(headers);
                ResponseEntity<Object> response=restTemplate.exchange("http://auth-service/auth/auth/v1/api/user/validatetoken", HttpMethod.GET,entity, Object.class);
                if(response.getStatusCode().is2xxSuccessful()){
                    request.setAttribute("userDetails",response.getBody());
                }
*/



            }

    } catch (Exception he) {
        he.printStackTrace();
    }

        return null;
    }
}