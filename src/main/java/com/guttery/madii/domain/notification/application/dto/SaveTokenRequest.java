package com.guttery.madii.domain.notification.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "토큰 저장 요청")
public record SaveTokenRequest(
        @NotBlank
        @Schema(description = "토큰", example = "fcm_token_value")
        String token,

        @NotBlank
        @Schema(description = "기기 ID", example = "device_id_value")
        String deviceId
) {
}
