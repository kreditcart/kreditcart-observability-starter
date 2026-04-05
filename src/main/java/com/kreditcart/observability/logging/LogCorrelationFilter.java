package com.kreditcart.observability.logging;

import com.kreditcart.observability.utils.ObservabilityConstants;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class LogCorrelationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            // 1. Get OpenTelemetry current span (if exists)
            Span currentSpan = Span.current();
            SpanContext spanContext = currentSpan.getSpanContext();

            String traceId;
            String spanId;

            if(spanContext.isValid()) {
                traceId = spanContext.getTraceId();
                spanId = spanContext.getSpanId();
            }else {
                // fallback if OTel not active
                traceId = UUID.randomUUID().toString();
                spanId = "0";
            }

            // 2. Put into MDC
            MDC.put(ObservabilityConstants.TRACE_ID, traceId);
            MDC.put(ObservabilityConstants.SPAN_ID, spanId);
            MDC.put(ObservabilityConstants.REQUEST_ID, UUID.randomUUID().toString());

            // 3. continue chain
            filterChain.doFilter(request, response);

        }finally {
            // 5. cleanup
            MDC.clear();
        }
    }
}
