package com.guttery.madii.domain.achievement.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.achievement.domain.model.Achievement;
import com.guttery.madii.domain.achievement.domain.repository.AchievementRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AchievementServiceHelper {
    public static Achievement findExistingAchievement(final AchievementRepository achievementRepository, final Long achievementId) {
        return achievementRepository.findById(achievementId)
                .orElseThrow(() -> CustomException.of(ErrorDetails.ACHIEVEMENT_NOT_FOUND));
    }

    public static void validateAchievementAchiever(final Achievement achievement, final Long userId) {
        if (!achievement.isAchievedBy(userId)) {
            throw CustomException.of(ErrorDetails.ACHIEVEMENT_ACHIEVER_NOT_MATCH);
        }
    }
}
