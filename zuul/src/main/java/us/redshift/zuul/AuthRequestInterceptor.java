package us.redshift.zuul;

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
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token == null) {
            return;
        }
        System.out.println("asd:"+token);

        requestTemplate.header(AUTHORIZATION_HEADER, token);
        requestTemplate.header("RequestTo", request.getRequestURI());
    }
}