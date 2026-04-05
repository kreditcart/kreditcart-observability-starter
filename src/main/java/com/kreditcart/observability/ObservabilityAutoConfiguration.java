package com.kreditcart.observability;

import com.kreditcart.observability.autoconfig.LoggingAutoConfig;
import com.kreditcart.observability.autoconfig.OpenTelemetryAutoConfig;
import com.kreditcart.observability.autoconfig.TracingAutoConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ObservabilityAutoConfiguration {

    @Bean
    public OpenTelemetryAutoConfig openTelemetryConfig() {
        return new OpenTelemetryAutoConfig();
    }

    @Bean
    public LoggingAutoConfig loggingConfig() {
        return new LoggingAutoConfig();
    }

    @Bean
    public TracingAutoConfig tracingConfig() {
        return new TracingAutoConfig();
    }
}
