package us.redshift.timesheet;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Calendar;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
public class TimesheetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimesheetApplication.class, args);
    }

    @Bean
    public Calendar getCalenderInstance() {
        Calendar c = Calendar.getInstance();
        return c;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


//    @Bean
//    public Config hazelCastConfig() {
//
//        Config config = new Config();
//        config.setInstanceName("hazelcast-cache");
//
//        MapConfig allSkills = new MapConfig();
//        allSkills.setTimeToLiveSeconds(20);
//        allSkills.setEvictionPolicy(EvictionPolicy.LFU);
//        config.getMapConfigs().put("skills", allSkills);
//
//        MapConfig allEmployee = new MapConfig();
//        allEmployee.setTimeToLiveSeconds(20);
//        allEmployee.setEvictionPolicy(EvictionPolicy.LFU);
//        config.getMapConfigs().put("employees", allEmployee);
//
//
//        MapConfig designation = new MapConfig();
//        designation.setTimeToLiveSeconds(20);
//        designation.setEvictionPolicy(EvictionPolicy.LFU);
//        config.getMapConfigs().put("designations", designation);
//
//        return config;
//    }


}
