package com.guttery.madii.domain.notification.infrastructure;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.guttery.madii.domain.notification.domain.service.NotificationClient;
import org.springframework.stereotype.Component;

@Component
public class FirebaseNotificationClient implements NotificationClient {
    public String sendNotification(String content, String token) {
        try {
            final Message message = Message.builder()
                    .setToken(token)
                    .putData("title", content)
                    .build();

            return FirebaseMessaging.getInstance().send(message);

        } catch (FirebaseMessagingException e) {
            return "Failed";
        }
    }
}
