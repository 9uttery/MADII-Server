package com.guttery.madii.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guttery.madii.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (request.getAttribute("exception") instanceof final CustomException e) {
            setResponse(response, e.getErrorDetailsStatus(), e.getErrorDetailsCode(), e.getErrorDetailsMessage());
        }
    }

    private void setResponse(HttpServletResponse response, int status, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);

        final Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("status", status);
        responseMap.put("code", code);
        responseMap.put("message", message);
        responseMap.put("data", null);
        responseMap.put("timestamp", LocalDateTime.now());

        final String responseJson = objectMapper.writeValueAsString(responseMap);
        response.getWriter().print(responseJson);
    }
}
