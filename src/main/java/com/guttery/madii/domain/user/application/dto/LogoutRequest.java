package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그아웃 요청")
public record LogoutRequest(
        @NotBlank
        @Schema(description = "로그아웃할 사용자의 리프레시토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzNzIwNzIwM30.")
        String refreshToken
) {
}
