package com.guttery.madii.domain.notification.application.service;

import com.guttery.madii.domain.notification.application.dto.NotificationData;
import com.guttery.madii.domain.notification.domain.model.Notification;
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
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UnfinishedAchievementNotificationSendService {
    private static final int UNFINISHED_ACHIEVEMENT_NOTIFICATION_TIME = 20;
    private static final String UNFINISHED_ACHIEVEMENT_NOTIFICATION_TITLE = "미완료 소확행 알림";
    private static final String UNFINISHED_ACHIEVEMENT_NOTIFICATION_MESSAGE = "오늘의 소확행을 완료하지 못했어요. 얼른 완료해보세요!";

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTokensRepository notificationTokensRepository;
    private final NotificationClient notificationClient;


    @Scheduled(cron = "0 0 20 * * *") // 매일 오후 8시에 실행
    @Transactional
    public void sendUnfinishedAchievementNotifications() {
        final List<User> users = getUsersWithUnfinishedAchievements();
        final List<String> tokens = getTokensByUserIds(users.stream().map(user -> user.getUserId().toString()).toList());
        final NotificationData notificationData = createNotificationData();
        sendNotifications(notificationData, tokens);
        saveNotifications(users, notificationData);
    }

    private List<User> getUsersWithUnfinishedAchievements() {
        final LocalDateTime targetTime = LocalDate.now().atStartOfDay().plusHours(UNFINISHED_ACHIEVEMENT_NOTIFICATION_TIME);

        return userRepository.findUsersWithUnfinishedAchievements(targetTime);
    }

    private List<String> getTokensByUserIds(List<String> userIds) {
        return notificationTokensRepository.getTokensByUserIds(userIds);
    }

    private NotificationData createNotificationData() {
        return new NotificationData(UNFINISHED_ACHIEVEMENT_NOTIFICATION_TITLE, UNFINISHED_ACHIEVEMENT_NOTIFICATION_MESSAGE);
    }

    private void sendNotifications(final NotificationData notificationData, final List<String> tokens) {
        notificationClient.sendSameNotificationForUsers(notificationData, tokens);
    }

    private void saveNotifications(final List<User> users, final NotificationData notificationData) {
        notificationRepository.bulkSave(Notification.createBulk(users, notificationData.title(), notificationData.contents()));
    }
}
