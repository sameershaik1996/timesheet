package us.redshift.timesheet;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Calendar;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
//@EnableCaching
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
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        Cache employeesCache = new ConcurrentMapCache("employees");
//        Cache designationsCache = new ConcurrentMapCache("designations");
//        Cache skillsCache = new ConcurrentMapCache("skills");
//        cacheManager.setCaches(Arrays.asList(employeesCache));
//        cacheManager.setCaches(Arrays.asList(designationsCache));
//        cacheManager.setCaches(Arrays.asList(skillsCache));
//        return cacheManager;
//    }


}
