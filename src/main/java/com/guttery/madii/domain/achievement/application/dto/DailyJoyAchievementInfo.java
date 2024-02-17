package com.guttery.madii.domain.achievement.application.dto;

import com.guttery.madii.domain.achievement.domain.model.Satisfaction;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일일 소확행 정보")
public record DailyJoyAchievementInfo(
        @Schema(description = "소확행 ID", example = "1")
        Long joyId,
        @Schema(description = "실천 ID", example = "1")
        Long achievementId,
        @Schema(description = "소확행 아이콘 번호", example = "1")
        Integer joyIconNum,
        @Schema(description = "소확행 내용", example = "붕어방 먹기")
        String contents,
        @Schema(description = "소확행 만족도", example = "EXCELLENT")
        Satisfaction satisfaction
) {
}
