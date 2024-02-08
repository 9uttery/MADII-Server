package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "마케팅 수신 동의 변경 요청 DTO")
public record UpdateMarketingAgreementRequest(
        @NotNull(message = "agreesMarketing은 필수입니다.")
        @Schema(description = "마케팅 수신 동의", example = "true", defaultValue = "true")
        boolean agreesMarketing
) {
}
