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
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.Calendar;

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

    @Scheduled(fixedRate = 6000)
    public void evictAllcachesAtIntervals() {
        System.out.println(LocalDate.now());
        evictAllCacheValues();
    }

    @CacheEvict(allEntries = true)
    public void evictAllCacheValues() {
    }

}
