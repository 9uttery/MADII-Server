package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "소확행 취소 요청")
public record CancelAchievementRequest(
        @NotNull
        @Schema(description = "소확행 ID", example = "1")
        Long achievementId
) {
}
