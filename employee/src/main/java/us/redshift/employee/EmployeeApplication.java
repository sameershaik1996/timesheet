package us.redshift.employee;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.dto.EmployeeDto;


import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan("us.redshift")
@EntityScan(basePackageClasses = {
		EmployeeApplication.class,
		Jsr310JpaConverters.class
})
@EnableJpaAuditing
public class EmployeeApplication {

	@Value( "${spring.jackson.time-zone}" )
	private String timeZone;



	@PostConstruct
	void init() {
		System.out.println(timeZone);
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
	}

	@Bean("mapper")
	public ModelMapper modelMapper() {
		ModelMapper mapper=new ModelMapper();
		mapper.typeMap(EmployeeDto.class,Employee.class).setPropertyCondition(Conditions.isNotNull());
		mapper.typeMap(Employee.class,Employee.class).setPropertyCondition(Conditions.isNotNull());

		return mapper;
	}


	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
