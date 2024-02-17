package com.guttery.madii.domain.achievement.domain.repository;

import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;

import java.time.LocalDate;

public interface AchievementQueryDslRepository {
    JoyPlaylistResponse getAchievementsInPlaylist(Long userId, LocalDate date);
}
