package com.guttery.madii.domain.achievement.domain.repository;

import com.guttery.madii.domain.achievement.application.dto.CalenderAchievementColorResponse;
import com.guttery.madii.domain.achievement.application.dto.CalenderDailyJoyAchievementResponse;
import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.user.domain.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AchievementQueryDslRepository {
    JoyPlaylistResponse getAchievementsInPlaylist(Long userId, LocalDateTime dateTime);

    CalenderAchievementColorResponse getAchievementsInCalender(Long userId, LocalDate startDate, LocalDate endDate);

    CalenderDailyJoyAchievementResponse getDailyJoyAchievementInfos(Long userId, LocalDate date);

    boolean checkJoyAlreadyInPlaylist(Joy joy, User achiever);
}
