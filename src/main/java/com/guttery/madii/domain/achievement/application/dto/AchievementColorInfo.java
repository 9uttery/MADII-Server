package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "소확행 색상 정보")
public record AchievementColorInfo(
        @Schema(description = "소확행 ID", example = "1")
        Long joyId,
        @Schema(description = "소확행 아이콘 번호", example = "1")
        Integer joyIconNum
) {
}
