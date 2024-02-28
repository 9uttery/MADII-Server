package com.guttery.madii.domain.notification.domain.service;

import com.guttery.madii.domain.notification.application.dto.NotificationData;

import java.util.List;

public interface NotificationClient {
    void sendNotification(NotificationData notificationData, String token);

    void sendNotificationForAll(NotificationData notificationData, List<String> tokens);
}
