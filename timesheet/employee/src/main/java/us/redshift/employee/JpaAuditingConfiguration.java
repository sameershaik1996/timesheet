package us.redshift.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.ServletContext;
import javax.xml.ws.spi.http.HttpContext;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<Long> auditorProvider(ServletContext servletContext) {
        System.out.println("in context");
        System.out.println("as111d:"+servletContext.getAttribute("details"));
        return () -> Optional.ofNullable(new Long(1));
    }
}