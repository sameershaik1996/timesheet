package us.redshift.timesheet;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import us.redshift.filter.model.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AuditorAwareConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        UserDetails ud = (UserDetails) request.getAttribute("userDetails");
//        System.out.println(ud.getUserName());
//        return Optional.of(ud.getUserName());
        return Optional.ofNullable(ud.getUserName());
    }
}
