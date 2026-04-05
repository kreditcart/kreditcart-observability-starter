package com.kreditcart.observability.autoconfig;

import com.kreditcart.observability.logging.LogCorrelationFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LoggingAutoConfig {
    @Bean
    public FilterRegistrationBean<LogCorrelationFilter> logCorrelationFilter() {

        FilterRegistrationBean<LogCorrelationFilter> bean =
                new FilterRegistrationBean<>();

        bean.setFilter(new LogCorrelationFilter());
        bean.setOrder(1); // highest priority for logging correlation

        return bean;
    }
}