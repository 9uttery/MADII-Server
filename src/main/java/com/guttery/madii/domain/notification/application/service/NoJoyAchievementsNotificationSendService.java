package com.guttery.madii.domain.notification.application.service;

import com.guttery.madii.domain.notification.application.dto.FlattedNotificationTokenDto;
import com.guttery.madii.domain.notification.application.dto.MessageData;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class NoJoyAchievementsNotificationSendService {
    private static final int DAY_CRITERIA = 7;
    private static final String NO_JOY_ACHIEVEMENT_NOTIFICATION_TITLE = "오늘의 행복이 %s님을 기다리고 있어요";
    private static final String NO_JOY_ACHIEVEMENT_NOTIFICATION_MESSAGE = "마지막으로 소확행을 실천한 지 일주일이 지났어요. 오늘 이어서 실천해 보시는 건 어때요?";

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTokensRepository notificationTokensRepository;
    private final NotificationClient notificationClient;

    @Scheduled(cron = "0 0 20 * * SUN") // 매주 일요일 오후 8시에 실행
    @Transactional
    public void sendNoJoyAchievementsNotifications() {
        final List<User> foundUsers = getUsersWithNoJoyAchievements();
        final List<FlattedNotificationTokenDto> flattedNotificationTokenDtos = notificationTokensRepository.getAllFlatted();
        sendNotifications(foundUsers, flattedNotificationTokenDtos);
        saveNotifications(createNotificationData());
    }

    private List<User> getUsersWithNoJoyAchievements() {
        return userRepository.findUsersWithNoJoyAchievementsForDays(DAY_CRITERIA);
    }

    private void sendNotifications(
            final List<User> users,
            final List<FlattedNotificationTokenDto> flattedNotificationTokenDtos) {

        final Map<String, List<String>> tokensByUserId = flattedNotificationTokenDtos.stream()
                .collect(Collectors.toMap(FlattedNotificationTokenDto::userId, FlattedNotificationTokenDto::tokens));

        final List<MessageData> messageDatas = users.stream()
                .filter(User::hasProfile)
                .flatMap(user -> tokensByUserId.getOrDefault(user.getUserId().toString(), Collections.emptyList()).stream()
                        .map(token -> createMessageData(String.format(NO_JOY_ACHIEVEMENT_NOTIFICATION_TITLE, user.getNickname()), token))
                ).toList();

        notificationClient.sendDifferentNotificationForUsers(messageDatas);
    }

    private MessageData createMessageData(final String contents, final String token) {
        return new MessageData(contents, NO_JOY_ACHIEVEMENT_NOTIFICATION_MESSAGE, token);
    }

    private NotificationData createNotificationData() {
        return new NotificationData(NO_JOY_ACHIEVEMENT_NOTIFICATION_TITLE, NO_JOY_ACHIEVEMENT_NOTIFICATION_MESSAGE);
    }

    private void saveNotifications(final NotificationData notificationData) {
        final List<User> allUsers = userRepository.findAll();

        notificationRepository.bulkSave(
                Notification.createdBulkFormatted(allUsers, notificationData.title(), NO_JOY_ACHIEVEMENT_NOTIFICATION_MESSAGE)
        );
    }
}
