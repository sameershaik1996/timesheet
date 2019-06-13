package us.redshift.timesheet;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
@ComponentScan("us.redshift")
@EntityScan(basePackageClasses = {
        TimesheetApplication.class,
        Jsr310JpaConverters.class
})
@EnableScheduling
public class TimesheetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimesheetApplication.class, args);
    }


    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
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

    @Scheduled(fixedRate = 60000 * 5)
    public void evictAllCachesAtIntervals() {
//        //System.out.println(LocalDateTime.now());
        evictAllCacheValues();
    }

    @CacheEvict(allEntries = true)
    public void evictAllCacheValues() {
    }

}
