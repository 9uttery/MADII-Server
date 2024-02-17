package com.guttery.madii.domain.achievement.application.service;

import com.guttery.madii.domain.achievement.application.dto.CancelAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.FinishAchievementRequest;
import com.guttery.madii.domain.achievement.domain.model.Achievement;
import com.guttery.madii.domain.achievement.domain.repository.AchievementRepository;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UpdateAchievementStatusService {
    private final AchievementRepository achievementRepository;

    @Transactional
    public void finishAchievement(final FinishAchievementRequest finishAchievementRequest, final UserPrincipal userPrincipal) {
        final Achievement foundAchievement = AchievementServiceHelper.findExistingAchievement(achievementRepository, finishAchievementRequest.achievementId());
        AchievementServiceHelper.validateAchievementAchiever(foundAchievement, userPrincipal.id());
        foundAchievement.finish(finishAchievementRequest.satisfaction());

        achievementRepository.save(foundAchievement);
    }

    @Transactional
    public void cancelAchievement(final CancelAchievementRequest cancelAchievementRequest, final UserPrincipal userPrincipal) {
        final Achievement foundAchievement = AchievementServiceHelper.findExistingAchievement(achievementRepository, cancelAchievementRequest.achievementId());
        AchievementServiceHelper.validateAchievementAchiever(foundAchievement, userPrincipal.id());
        foundAchievement.cancel();

        achievementRepository.save(foundAchievement);
    }
}
