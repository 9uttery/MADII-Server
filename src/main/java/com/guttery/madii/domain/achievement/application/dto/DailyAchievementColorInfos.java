package com.guttery.madii.domain.achievement.application.dto;

import java.util.List;

public record DailyAchievementColorInfos(
        String date,
        List<AchievementColorInfo> achievementColorInfos
) {
}
