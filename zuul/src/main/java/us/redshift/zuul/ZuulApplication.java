package us.redshift.zuul;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import us.redshift.zuul.exception.RErrorDecoder;
import us.redshift.zuul.exception.RestTemplateResponseErrorHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;


@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ZuulApplication {
	@Bean
	Gson getGsonObject(){
		Gson gson = new GsonBuilder().create();
		return gson;
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = restTemplateBuilder
				.errorHandler(new RestTemplateResponseErrorHandler())
				.build();
		return restTemplate;
	}


	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

}
