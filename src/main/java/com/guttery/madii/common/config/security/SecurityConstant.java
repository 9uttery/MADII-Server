package com.guttery.madii.common.config.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstant {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_USER = "ROLE_USER";
    public static final long ACCESS_TOKEN_PERIOD = (long) 1000 * 60 * 60 * 24; // 1일
    public static final long REFRESH_TOKEN_PERIOD = (long) 1000 * 60 * 60 * 24 * 14; // 14일

    // Immutable List 사용
    public static final List<String> AUTH_WHITELIST = List.of(
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/health-check",
            "/favicon.ico",
            "/auth/**"

            // TODO: 인증 필요 없는 API 엔드포인트 추가
    );
}