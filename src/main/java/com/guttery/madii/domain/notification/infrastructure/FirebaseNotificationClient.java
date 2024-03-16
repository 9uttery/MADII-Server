package com.guttery.madii.domain.notification.infrastructure;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.notification.application.dto.MessageData;
import com.guttery.madii.domain.notification.application.dto.NotificationData;
import com.guttery.madii.domain.notification.domain.service.NotificationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class FirebaseNotificationClient implements NotificationClient {
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendNotification(final NotificationData notificationData, final String token) {
        if (token == null) {
            return;
        }

        try {
            final Message message = Message.builder()
                    .setToken(token)
                    .setNotification(createNotification(notificationData.title(), notificationData.contents()))
                    .build();

            firebaseMessaging.send(message);

        } catch (final FirebaseMessagingException e) {
            log.error("알림 전송 에러, FCM 토큰 값: {}", token, e);
            throw CustomException.of(ErrorDetails.FIREBASE_NOTIFICATION_SEND_ERROR);
        }
    }

    @Override
    public void sendSameNotificationForUsers(final NotificationData notificationData, List<String> tokens) {
        if (isEmpty(tokens)) {
            return;
        }

        try {
            final MulticastMessage message = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(createNotification(notificationData.title(), notificationData.contents()))
                    .build();

            firebaseMessaging.sendEachForMulticast(message);
        } catch (final FirebaseMessagingException e) {
            log.error("전체 알림 전송 에러", e);
            throw CustomException.of(ErrorDetails.FIREBASE_NOTIFICATION_SEND_ERROR);
        }
    }

    private boolean isEmpty(final List<String> tokens) {
        return tokens == null || tokens.isEmpty();
    }

    private Notification createNotification(final String title, final String contents) {
        return Notification.builder()
                .setTitle(title)
                .setBody(contents)
                .build();
    }


    @Override
    public void sendDifferentNotificationForUsers(List<MessageData> messageDatas) {
        List<Message> messages = messageDatas.stream()
                .map(messageData -> Message.builder()
                        .setToken(messageData.token())
                        .setNotification(createNotification(messageData.title(), messageData.contents()))
                        .build())
                .toList();

        try {
            firebaseMessaging.sendEach(messages);
        } catch (FirebaseMessagingException e) {
            log.error("다수 알림 전송 에러", e);
            throw CustomException.of(ErrorDetails.FIREBASE_NOTIFICATION_SEND_ERROR);
        }
    }
}
