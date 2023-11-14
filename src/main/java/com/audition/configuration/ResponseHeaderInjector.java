package com.audition.configuration;

import io.prometheus.client.exemplars.tracer.otel_agent.OpenTelemetryAgentSpanContextSupplier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;

@ControllerAdvice
@AllArgsConstructor
public class ResponseHeaderInjector implements HandlerInterceptor {

    private OpenTelemetryAgentSpanContextSupplier openTelemetryAgentSpanContextSupplier;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.addHeader("X-Audition-App-TraceId", openTelemetryAgentSpanContextSupplier.getTraceId());
        response.addHeader("X-Audition-App-SpanId", openTelemetryAgentSpanContextSupplier.getSpanId());
        return true;
    }
}
