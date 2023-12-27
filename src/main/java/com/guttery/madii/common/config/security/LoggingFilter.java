package com.guttery.madii.common.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);

            if (!isHealthCheckRequest(wrappedRequest)) {
                logRequest(wrappedRequest);
                logResponse(wrappedResponse);
            }
        } finally {
            MDC.clear();
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) throws IOException {
        String contentType = request.getContentType();

        String queryString = request.getQueryString();
        log.info("Request: {} uri=[{}] content-type=[{}]",
                request.getMethod(),
                queryString == null ? request.getRequestURI() : request.getRequestURI() + "?" + queryString,
                contentType);

        if (contentType != null && !contentType.startsWith("multipart/form-data")) {
            byte[] content = request.getContentAsByteArray();
            if (content.length > 0) {
                log.info("Request Payload: {}", new String(content, request.getCharacterEncoding()));
            }
        }
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        byte[] content = response.getContentAsByteArray();

        if (content.length > 0) {
            log.info("Response Payload: {}", new String(content, StandardCharsets.UTF_8)); // 문자열 생성 시 인코딩 사용
        }
    }


    private boolean isHealthCheckRequest(HttpServletRequest request) {
        return "/health".equals(request.getRequestURI()) && "GET".equalsIgnoreCase(request.getMethod());
    }
}
