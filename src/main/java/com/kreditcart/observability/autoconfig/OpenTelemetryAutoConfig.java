package com.kreditcart.observability.autoconfig;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.ServiceAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@AutoConfiguration
public class OpenTelemetryAutoConfig {
    @Value("${otel.service.name}")
    private String appName;

    @Value("${otel.exporter.otlp.endpoint:http://localhost:4317}")
    private String exporterEndPoint;

    @Bean
    public OtlpGrpcSpanExporter otlpGrpcSpanExporter() {
        return OtlpGrpcSpanExporter.builder()
                .setEndpoint(exporterEndPoint)
                .setTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public SdkTracerProvider sdkTracerProvider(OtlpGrpcSpanExporter exporter) {
        return SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
                .setResource(Resource.getDefault().toBuilder()
                        .put(ServiceAttributes.SERVICE_NAME, appName)
                        .build())
                .setSampler(Sampler.alwaysOn()) // Todo: disable it in prod
                .build();
    }

    @Bean
    public OpenTelemetry openTelemetry(SdkTracerProvider tracerProvider) {

        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .build();
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("observability-starter");
    }
}
