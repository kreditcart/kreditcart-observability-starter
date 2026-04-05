package com.kreditcart.observability.tracing;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class HttpTracingFilter extends OncePerRequestFilter {

    private final Tracer tracer;

    public HttpTracingFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Span span = tracer.spanBuilder(request.getMethod() + " " + request.getRequestURI())
                .startSpan();

        try (Scope scope = span.makeCurrent()) {

            response.setHeader("X-Trace-Id", span.getSpanContext().getTraceId());
            response.setHeader("X-Span-Id", span.getSpanContext().getSpanId());

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            span.recordException(e);
            throw e;
        } finally {
            span.end();
        }
    }
}