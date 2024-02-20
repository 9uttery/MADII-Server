package com.guttery.madii.domain.placeholder.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "플레이스홀더 응답")
public record PlaceholderResponse(
        @Schema(description = "플레이스홀더 내용", example = "플레이스홀더 내용")
        String contents
) {
}
