package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리프레시 후 토큰 응답")
public record RefreshResponse(
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIs", defaultValue = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIs")
        String accessToken,
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIs", defaultValue = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIs")
        String refreshToken,
        @Schema(description = "마케팅 수신 동의 여부", example = "true", defaultValue = "true")
        boolean agreedMarketing,
        @Schema(description = "프로필 등록 여부", example = "true", defaultValue = "true")
        boolean hasProfile
) {
}
