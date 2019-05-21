package us.auth.filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FilterApplication {




	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		//registrationBean.setFilter(new JwtAuthenticationFilter(new JwtTokenProvider(jwtSecret,jwtExpirationInMs,new ModelMapper())));
		registrationBean.setFilter(jwtAuthenticationFilter());


		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(FilterApplication.class, args);
	}

}
