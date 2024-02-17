package com.guttery.madii.domain.achievement.application.service;

import com.guttery.madii.domain.achievement.application.dto.CalenderAchievementColorResponse;
import com.guttery.madii.domain.achievement.application.dto.CalenderDailyJoyAchievementResponse;
import com.guttery.madii.domain.achievement.domain.repository.AchievementRepository;
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
public class CalenderService {
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private final JoyRepository joyRepository;

    @Transactional(readOnly = true)
    public CalenderAchievementColorResponse getAchievementColorInfos(final LocalDate startDate, final LocalDate endDate, final UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        return achievementRepository.getAchievementsInCalender(user.getUserId(), startDate, endDate);
    }

    @Transactional(readOnly = true)
    public CalenderDailyJoyAchievementResponse getDailyJoyAchievementInfos(final LocalDate date, final UserPrincipal userPrincipal) {
        final User user = UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        return achievementRepository.getDailyJoyAchievementInfos(user.getUserId(), date);
    }
}
