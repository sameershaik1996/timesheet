package us.redshift.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class FilterApplication {
	@RequestMapping("test")
	public void test(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = requestAttributes.getRequest();
		System.out.println(request.getAttribute("test").toString());
	}

	@Bean
	public RestTemplate getTemplate(){
		return new RestTemplate();
	}

	@Value("${zuul.auth.path}")
	public  String authUrl;
	@Bean
	public FilterRegistrationBean authFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		//registrationBean.setFilter(new JwtAuthenticationFilter(new JwtTokenProvider(jwtSecret,jwtExpirationInMs,new ModelMapper())));
		registrationBean.setFilter(new AuthFilter(getTemplate(),authUrl));


		return registrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(FilterApplication.class, args);
	}

}
