package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "일일 소확행 색상 정보 리스트")
public record DailyAchievementColorInfos(
        @Schema(description = "날짜", example = "2021-10-01")
        String date,
        @Schema(description = "소확행 색상 정보 리스트")
        List<Integer> achievementColorInfos
) {
}
