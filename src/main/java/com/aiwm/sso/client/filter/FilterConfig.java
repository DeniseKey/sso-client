package com.aiwm.sso.client.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Created by fengmc on 2019/3/27
 */
@Configuration
public class FilterConfig {
    @Bean
    public SsoClientFilter clientFilter(){
        return new SsoClientFilter();
    }
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("clientFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("SsoClientFilter");
        registration.setOrder(1);
        return registration;
    }
}
