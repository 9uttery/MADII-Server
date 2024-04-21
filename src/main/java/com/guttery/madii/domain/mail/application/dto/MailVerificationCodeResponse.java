package com.guttery.madii.domain.mail.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메일 인증 코드 응답")
public record MailVerificationCodeResponse(
        @Schema(description = "인증 코드", example = "123456")
        String code
) {
}
