package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "일반 로그인 요청")
public record NormalLoginRequest(
        @NotBlank
        @Schema(description = "로그인 아이디", example = "9uttery", defaultValue = "9uttery")
        String loginId,
        @NotBlank
        @Schema(description = "비밀번호", example = "EjflEjfl1234", defaultValue = "EjflEjfl1234")
        String password
) {
}
