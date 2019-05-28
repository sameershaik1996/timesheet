package us.redshift.auth;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import us.redshift.auth.domain.User;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class AuthApplication {

	@Value( "${spring.jackson.time-zone}" )
	private String timeZone;
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper=new ModelMapper();
		mapper.typeMap(User.class,User.class).setPropertyCondition(Conditions.isNotNull());
		return mapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
