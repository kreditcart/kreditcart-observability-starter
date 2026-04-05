package com.kreditcart.observability.autoconfig;

import com.kreditcart.observability.tracing.HttpTracingFilter;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class TracingAutoConfig {
    @Bean
    public FilterRegistrationBean<HttpTracingFilter> tracingFilter(Tracer tracer) {

        FilterRegistrationBean<HttpTracingFilter> bean =
                new FilterRegistrationBean<HttpTracingFilter>();

        bean.setFilter(new HttpTracingFilter(tracer));
        bean.setOrder(0);

        return bean;
    }
}
