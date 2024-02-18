package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일일 소확행 색상 정보 리스트")
public final class DailyAchievementColorInfos {
    @Schema(description = "날짜", example = "2021-10-01")
    private final String date;
    @Schema(description = "소확행 색상 정보 리스트")
    private final List<Integer> achievementColorInfos;

    public DailyAchievementColorInfos(String date, List<AchievementColorInfo> achievementColorInfos) {
        this.date = date;
        this.achievementColorInfos = achievementColorInfos.stream()
                .map(AchievementColorInfo::colorNum)
                .limit(9)
                .toList();
    }
}
