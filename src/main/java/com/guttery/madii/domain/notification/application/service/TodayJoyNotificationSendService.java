package com.guttery.madii.domain.notification.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.joy.domain.model.RecommendedJoys;
import com.guttery.madii.domain.joy.domain.repository.RecommendedJoysRepository;
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

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class TodayJoyNotificationSendService {
    private static final String TODAY_JOY_NOTIFICATION_TITLE = "오늘의 소확행이 열렸어요!";
    private static final String FORMATTED_TODAY_JOY_NOTIFICATION_MESSAGE = "%s님, %s를 통해 오늘 하루 행복을 충전해요.";

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTokensRepository notificationTokensRepository;
    private final RecommendedJoysRepository recommendedJoysRepository;
    private final NotificationClient notificationClient;

    @Scheduled(cron = "0 0 11 * * *") // 매일 오전 11시에 실행
    @Transactional
    public void sendAndSaveTodayJoyNotifications() {
        final List<User> users = userRepository.findAll();
        final List<FlattedNotificationTokenDto> flattedNotificationTokenDtos = notificationTokensRepository.getAllFlatted();

        final String todayJoyContents = getTodayJoyContents(LocalDate.now());
        sendNotifications(todayJoyContents, users, flattedNotificationTokenDtos);

        final NotificationData createdNotificationData = createNotificationData(LocalDate.now());
        saveNotifications(createdNotificationData);
    }

    private String getTodayJoyContents(LocalDate today) {
        final RecommendedJoys foundRecommendedJoys = recommendedJoysRepository.findById(YearMonth.of(today.getYear(), today.getMonth()))
                .orElseThrow(() -> CustomException.of(ErrorDetails.TODAY_JOY_NOT_FOUND));
        return foundRecommendedJoys.getDailyJoyContentsByDay(today.getDayOfMonth());
    }

    private MessageData createMessageData(final String contents, final String token) {
        return new MessageData(TODAY_JOY_NOTIFICATION_TITLE, contents, token);
    }

    private void sendNotifications(
            final String todayJoyName,
            final List<User> users,
            final List<FlattedNotificationTokenDto> flattedNotificationTokenDtos) {

        final Map<String, List<String>> tokensByUserId = flattedNotificationTokenDtos.stream()
                .collect(Collectors.toMap(FlattedNotificationTokenDto::userId, FlattedNotificationTokenDto::tokens));

        final List<MessageData> messageDatas = users.stream()
                .flatMap(user -> tokensByUserId.getOrDefault(user.getUserId().toString(), Collections.emptyList()).stream()
                        .map(token -> createMessageData(String.format(FORMATTED_TODAY_JOY_NOTIFICATION_MESSAGE, user.getNickname(), todayJoyName), token))
                ).toList();

        notificationClient.sendDifferentNotificationForUsers(messageDatas);
    }

    private NotificationData createNotificationData(final LocalDate today) {
        final RecommendedJoys foundRecommendedJoys = recommendedJoysRepository.findById(YearMonth.of(today.getYear(), today.getMonth()))
                .orElseThrow(() -> CustomException.of(ErrorDetails.TODAY_JOY_NOT_FOUND));

        return new NotificationData(TODAY_JOY_NOTIFICATION_TITLE, foundRecommendedJoys.getDailyJoyContentsByDay(today.getDayOfMonth()));
    }


    private void saveNotifications(final NotificationData notificationData) {
        final List<User> allUsers = userRepository.findAll();

        notificationRepository.bulkSave(
                Notification.createdBulkFormatted(allUsers, TODAY_JOY_NOTIFICATION_TITLE, notificationData.contents(), FORMATTED_TODAY_JOY_NOTIFICATION_MESSAGE)
        );
    }
}
