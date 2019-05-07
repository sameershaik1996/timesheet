package us.redshift.authutility.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan
@Getter
@Setter
@NoArgsConstructor
public class AppUtil {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Bean
    @DependsOn({"tokenProvider","modelMapper"})
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(new JwtTokenProvider(jwtSecret,jwtExpirationInMs,new ModelMapper()));
    }

    @Bean
    @DependsOn({"tokenProvider","modelMapper"})
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //registrationBean.setFilter(new JwtAuthenticationFilter(new JwtTokenProvider(jwtSecret,jwtExpirationInMs,new ModelMapper())));
        registrationBean.setFilter(jwtAuthenticationFilter());



        return registrationBean;
    }



    @Bean("modelMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
