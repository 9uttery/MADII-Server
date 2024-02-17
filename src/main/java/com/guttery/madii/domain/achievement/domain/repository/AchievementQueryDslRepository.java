package com.guttery.madii.domain.achievement.domain.repository;

import com.guttery.madii.domain.achievement.application.dto.CalenderAchievementColorResponse;
import com.guttery.madii.domain.achievement.application.dto.CalenderDailyJoyAchievementResponse;
import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;

import java.time.LocalDate;

public interface AchievementQueryDslRepository {
    JoyPlaylistResponse getAchievementsInPlaylist(Long userId, LocalDate date);

    CalenderAchievementColorResponse getAchievementsInCalender(Long userId, LocalDate startDate, LocalDate endDate);

    CalenderDailyJoyAchievementResponse getDailyJoyAchievementInfos(Long userId, LocalDate date);
}
