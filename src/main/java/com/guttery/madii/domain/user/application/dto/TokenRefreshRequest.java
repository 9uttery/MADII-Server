package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "토큰 리프레시 요청")
public record TokenRefreshRequest(
        @Schema(description = "리프레시 토큰")
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        String refreshToken
) {
}
