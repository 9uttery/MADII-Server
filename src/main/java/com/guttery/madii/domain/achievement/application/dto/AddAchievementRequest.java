package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "소확행 플레이리스트에 추가 요청")
public record AddAchievementRequest(
        @NotNull
        @Schema(description = "소확행 ID", example = "1")
        Long joyId
) {
}
