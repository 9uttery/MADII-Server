package com.guttery.madii.domain.notification.infrastructure;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.notification.application.dto.NotificationData;
import com.guttery.madii.domain.notification.domain.service.NotificationClient;
import org.springframework.stereotype.Component;

@Component
public class FirebaseNotificationClient implements NotificationClient {
    private static final String NOTIFICATION_TITLE_KEY = "title";
    private static final String NOTIFICATION_MESSAGE_KEY = "message";

    public String sendNotification(final NotificationData notificationData, final String token) {
        try {
            final Message message = Message.builder()
                    .setToken(token)
                    .putData(NOTIFICATION_TITLE_KEY, notificationData.title())
                    .putData(NOTIFICATION_MESSAGE_KEY, notificationData.contents())
                    .build();

            return FirebaseMessaging.getInstance().send(message);

        } catch (final FirebaseMessagingException e) {
            throw CustomException.of(ErrorDetails.FIREBASE_NOTIFICATION_SEND_ERROR);
        }
    }
}
