package com.kreditcart.observability.utils;

import org.slf4j.MDC;

public class TraceContextHelper {
    public static void setTraceId(String traceId) {
        MDC.put(ObservabilityConstants.TRACE_ID, traceId);
    }

    public static void setSpanId(String spanId) {
        MDC.put(ObservabilityConstants.SPAN_ID, spanId);
    }

    public static void setRequestId(String requestId) {
        MDC.put(ObservabilityConstants.REQUEST_ID, requestId);
    }

    public static String getTraceId() {
        return MDC.get(ObservabilityConstants.TRACE_ID);
    }

    public static void clear() {
        MDC.clear();
    }
}
