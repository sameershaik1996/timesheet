package us.redshift.zuul;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.redshift.zuul.filter.ErrorFilter;
import us.redshift.zuul.filter.PostFilter;
import us.redshift.zuul.filter.PreFilter;
import us.redshift.zuul.filter.RouteFilter;

@Configuration
public class ZuulConfiguration {


    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }
}


