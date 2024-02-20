package com.guttery.madii.domain.placeholder.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "플레이스홀더 수정 요청")
public record UpdatePlaceholderRequest(
        @NotBlank
        @Schema(description = "플레이스홀더 내용", example = "플레이스홀더 내용")
        String contents
) {
}
