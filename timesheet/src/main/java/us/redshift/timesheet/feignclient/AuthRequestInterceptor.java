package us.redshift.timesheet.feignclient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthRequestInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String STARTS_WITH = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FtZWVyMUByZWQtc2hpZnQudXMiLCJlbXBsb3llZUlkIjozLCJpYXQiOjE1NTkyMjA2MDYsImV4cCI6MTU1OTgyNTQwNn0.AAQav8RAY-2yxuDtokkkF705HowyOT9G1q0fxyahjglclPTUuiCo_lIP8r6wJ1VzEXgGZ7hn4-UzPO_ytchlsQ";
//        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token == null) {
            return;
        }
        requestTemplate.header(AUTHORIZATION_HEADER, token);
    }

}
