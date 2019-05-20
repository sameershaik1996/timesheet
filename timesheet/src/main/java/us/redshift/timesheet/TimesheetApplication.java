package us.redshift.timesheet;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
public class TimesheetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimesheetApplication.class, args);
    }

    @Bean
    public Calendar getCalenderInstance(){
        Calendar c = Calendar.getInstance();
        return c;
    }


    @Bean
    public ModelMapper modelMapper() {


        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }


}
