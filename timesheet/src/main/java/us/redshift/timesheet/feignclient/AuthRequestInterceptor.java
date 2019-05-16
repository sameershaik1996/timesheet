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
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkByZWQtc2hpZnQudXMiLCJlbXBsb3llZUlkIjowLCJpYXQiOjE1NTc5MDAxNDcsImV4cCI6MTU1ODUwNDk0N30.YeHGCsUp8VeE9S192Flvr4tOTUw8H55F6yC1xBB-4C8VUxynspHJRs2EsnLyUE9Tnw-7tvY9s9j3DdYsj5Auhw";
//                request.getHeader(AUTHORIZATION_HEADER);
        if (token == null) {
            return;
        }
        requestTemplate.header(AUTHORIZATION_HEADER, token);
    }
}
