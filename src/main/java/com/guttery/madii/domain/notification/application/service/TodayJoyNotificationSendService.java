package com.guttery.madii.domain.notification.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.joy.domain.model.RecommendedJoys;
import com.guttery.madii.domain.joy.domain.repository.RecommendedJoysRepository;
import com.guttery.madii.domain.notification.application.dto.NotificationData;
import com.guttery.madii.domain.notification.domain.model.Notification;
import com.guttery.madii.domain.notification.domain.model.NotificationTokens;
import com.guttery.madii.domain.notification.domain.repository.NotificationRepository;
import com.guttery.madii.domain.notification.domain.repository.NotificationTokensRepository;
import com.guttery.madii.domain.notification.domain.service.NotificationClient;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TodayJoyNotificationSendService {
    private static final String TODAY_JOY_NOTIFICATION_TITLE = "오늘의 소확행";

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTokensRepository notificationTokensRepository;
    private final RecommendedJoysRepository recommendedJoysRepository;
    private final NotificationClient notificationClient;

    @Scheduled(cron = "0 0 11 * * *") // 매일 오전 11시에 실행
    @Transactional
    public void sendAndSaveTodayJoyNotifications() {
        final NotificationData createdNotificationData = createNotificationData(LocalDate.now());
        // 유저 토큰 한번에 다 가져오는 거 만들기 (몽고 템플릿 안 쓰고 가능한가..?)
        final List<NotificationTokens> allNotificationTokens = notificationTokensRepository.findAll();

        sendNotifications(createdNotificationData, allNotificationTokens);
        saveNotifications(createdNotificationData);
    }

    private NotificationData createNotificationData(final LocalDate today) {
        final RecommendedJoys foundRecommendedJoys = recommendedJoysRepository.findById(YearMonth.of(today.getYear(), today.getMonth()))
                .orElseThrow(() -> CustomException.of(ErrorDetails.TODAY_JOY_NOT_FOUND));

        return new NotificationData(TODAY_JOY_NOTIFICATION_TITLE, foundRecommendedJoys.getDailyJoyContentsByDay(today.getDayOfMonth()));
    }

    private void sendNotifications(final NotificationData notificationData, final List<NotificationTokens> allNotificationTokens) {
        final List<String> allTokens = allNotificationTokens.stream()
                .flatMap(notificationTokens -> notificationTokens.getAllNotificationTokens().stream())
                .toList();

        notificationClient.sendNotificationForAll(notificationData, allTokens);
    }

    // JPA 배치 처리로 성능 높이기 (추후)
    private void saveNotifications(final NotificationData notificationData) {
        final List<User> allUsers = userRepository.findAll();

        allUsers.forEach(user ->
                notificationRepository.save(
                        Notification.create(
                                user, TODAY_JOY_NOTIFICATION_TITLE, notificationData.contents()
                        )
                )
        );
    }
}
