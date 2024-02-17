package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "일일 소확행 플레이리스트")
public record DailyJoyPlaylist(
        @Schema(description = "소확행 플레이리스트 날짜", example = "2021-08-01")
        LocalDate date,
        @Schema(description = "소확행 목록")
        List<JoyAchievementInfo> joyAchievementInfos
) {
}
