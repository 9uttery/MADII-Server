package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "캘린더 소확행 색상 조회 응답")
public record CalenderAchievementColorResponse(
        @Schema(description = "소확행 색상 정보 리스트")
        List<DailyAchievementColorInfos> dailyAchievementColorInfos
) {
}
