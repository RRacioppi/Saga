package com.example.saga.orderservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class CookbookServletFilter implements Filter {
    public static final String CORRELATION_ID_HEADER_NAME = "REQUESTID";
    public static final String CORRELATION_ID_LOG_VAR_NAME = "requestId";

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String correlationId = req.getHeader(CORRELATION_ID_HEADER_NAME);
        if (correlationId == null || "".equals(correlationId.trim())) {
            correlationId = generateUniqueCorrelationId();
        }

        resp.setHeader(CORRELATION_ID_HEADER_NAME, correlationId);
        try (MDC.MDCCloseable m = MDC.putCloseable(CORRELATION_ID_LOG_VAR_NAME, correlationId)) {
            // Access Log IN
            long accessInTimeMillis = System.currentTimeMillis();
            log.info("eventType={}, remoteHost={}, method={}, uri={}, protocol={}",
                    "ACCESS_IN",
                    req.getRemoteHost(),
                    req.getMethod(),
                    req.getRequestURI(),
                    req.getProtocol());
            chain.doFilter(request, response);
            // Access Log OUT
            long responseTimeMillis = System.currentTimeMillis() - accessInTimeMillis;
            log.info("eventType={}, remoteHost={}, method={}, uri={}, protocol={}, api={}, status={}, duration={}",
                    "ACCESS_OUT",
                    req.getRemoteHost(),
                    req.getMethod(),
                    req.getRequestURI(),
                    req.getProtocol(),
                    req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE),
                    resp.getStatus(),
                    responseTimeMillis);
        }
    }

    private String generateUniqueCorrelationId() {
        return new StringBuilder(46).append("generated:")
                .append(UUID.randomUUID().toString())
                .toString();
    }
}


