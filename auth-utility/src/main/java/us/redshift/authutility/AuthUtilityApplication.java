package us.redshift.authutility;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import us.redshift.authutility.security.JwtAuthenticationFilter;
import us.redshift.authutility.security.JwtTokenProvider;

@SpringBootApplication

public class AuthUtilityApplication {

	/*@Value("${app.jwtSecret}")
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

*/

	@Bean()
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthUtilityApplication.class, args);
	}

}
