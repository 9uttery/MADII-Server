package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "캘린더 소확행 목록 상세 조회 응답")
public record CalenderDailyJoyAchievementResponse(
        List<DailyJoyAchievementInfo> dailyJoyAchievementInfos
) {
}
