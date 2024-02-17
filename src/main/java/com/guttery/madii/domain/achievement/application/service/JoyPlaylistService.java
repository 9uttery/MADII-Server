package com.guttery.madii.domain.achievement.application.service;

import com.guttery.madii.domain.achievement.application.dto.AddAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;
import com.guttery.madii.domain.achievement.application.dto.MoveAchievementToTodayRequest;
import com.guttery.madii.domain.achievement.domain.model.Achievement;
import com.guttery.madii.domain.achievement.domain.model.FinishInfo;
import com.guttery.madii.domain.achievement.domain.repository.AchievementRepository;
import com.guttery.madii.domain.joy.application.service.JoyServiceHelper;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.joy.domain.repository.JoyRepository;
import com.guttery.madii.domain.user.application.service.UserServiceHelper;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Service
public class JoyPlaylistService {
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private final JoyRepository joyRepository;

    @Transactional
    public void addAchievementInPlaylist(final AddAchievementRequest addAchievementRequest, final UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final Joy joy = JoyServiceHelper.findExistingJoy(joyRepository, addAchievementRequest.joyId());
        final Achievement newAchievement = Achievement.create(user, joy, FinishInfo.createNotFinished());

        achievementRepository.save(newAchievement);
    }

    @Transactional(readOnly = true)
    public JoyPlaylistResponse getAchievementsInPlaylist(final UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        return achievementRepository.getAchievementsInPlaylist(user.getUserId(), LocalDate.now());
    }

    @Transactional
    public void deleteAchievementInPlaylist(final Long achievementId, final UserPrincipal userPrincipal) {
        final Achievement foundAchievement = AchievementServiceHelper.findValidAchievement(achievementRepository, achievementId, userPrincipal);

        achievementRepository.delete(foundAchievement);
    }

    @Transactional
    public void moveAchievementInPlaylist(final MoveAchievementToTodayRequest moveAchievementToTodayRequest, final UserPrincipal userPrincipal) {
        final Achievement foundAchievement = AchievementServiceHelper.findValidAchievement(achievementRepository, moveAchievementToTodayRequest.achievementId(), userPrincipal);
        final Achievement newAchievement = foundAchievement.copyForMove();

        achievementRepository.delete(foundAchievement);
        achievementRepository.save(newAchievement);
    }
}
