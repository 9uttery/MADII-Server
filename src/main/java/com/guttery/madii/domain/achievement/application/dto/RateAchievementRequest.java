package com.guttery.madii.domain.achievement.application.dto;

import com.guttery.madii.domain.achievement.domain.model.Satisfaction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "실천 만족도 수정 요청")
public record RateAchievementRequest(
        @NotNull
        @Schema(description = "실천 ID", example = "1")
        Long achievementId,
        @NotNull
        @Schema(description = "만족도", example = "SO_SO")
        Satisfaction satisfaction
) {
}
