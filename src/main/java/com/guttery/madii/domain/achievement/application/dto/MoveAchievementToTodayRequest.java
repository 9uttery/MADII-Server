package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "실천을 오늘로 이동 요청")
public record MoveAchievementToTodayRequest(
        @Schema(description = "실천 ID", example = "1")
        Long achievementId
) {
}
