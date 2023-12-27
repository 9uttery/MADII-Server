package com.guttery.madii.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guttery.madii.common.exception.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setResponse(response);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(ErrorDetails.UNAUTHORIZED_ROLE.getStatus());

        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("status", ErrorDetails.UNAUTHORIZED_ROLE.getStatus());
        responseMap.put("code", ErrorDetails.UNAUTHORIZED_ROLE.getCode());
        responseMap.put("message", ErrorDetails.UNAUTHORIZED_ROLE.getMessage());
        responseMap.put("data", null);
        responseMap.put("timestamp", LocalDateTime.now());

        String responseJson = objectMapper.writeValueAsString(responseMap);
        response.getWriter().print(responseJson);
    }
}
