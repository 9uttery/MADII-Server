package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원가입 요청")
public record SignUpRequest(
        @NotBlank(message = "loginId는 필수입니다.")
        @Schema(description = "로그인 아이디", example = "9uttery", defaultValue = "9uttery")
        String loginId,
        @NotBlank(message = "password는 필수입니다.")
        @Schema(description = "비밀번호", example = "EjflEjfl1234", defaultValue = "EjflEjfl1234")
        String password,

        @NotNull(message = "acceptsMarketing은 필수입니다.")
        @Schema(description = "마케팅 수신 동의", example = "true", defaultValue = "true")
        Boolean acceptsMarketing
) {
}
