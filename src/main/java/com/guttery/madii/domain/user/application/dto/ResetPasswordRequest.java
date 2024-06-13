package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "애플 로그인 요청")
public record ResetPasswordRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Schema(description = "이메일", example = "9uttery@gmail.com")
        String email,
        @NotBlank(message = "새 비밀번호는 필수입니다.")
        @Schema(description = "비밀번호", example = "EjflEjfl1234")
        String password
) {
}
