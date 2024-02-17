package com.guttery.madii.domain.achievement.application.dto;

import com.guttery.madii.domain.achievement.domain.model.Satisfaction;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "소확행 및 실천 정보")
public record JoyAchievementInfo(
        @Schema(description = "소확행 아이디", example = "11")
        Long joyId,
        @Schema(description = "소확행 썸네일 아이콘 번호", example = "3")
        Integer joyIconNum,
        @Schema(description = "소확행 내용", example = "낮잠자기")
        String contents,
        @Schema(description = "소확행 실천 여부", example = "true")
        Boolean isAchieved,
        @Schema(description = "만족도 평가", example = "SO_SO")
        Satisfaction satisfaction
) {
}
