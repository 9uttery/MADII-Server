package com.guttery.madii.domain.notification.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "notificationtokens")
public class NotificationTokens {
    @MongoId
    private String id;
    private Map<String, String> tokenByDeviceId;

    public static NotificationTokens createEmpty(String userId) {
        return new NotificationTokens(userId, new HashMap<>());
    }

    public void addToken(final String deviceId, final String token) {
        tokenByDeviceId.put(deviceId, token);
    }

    public void removeToken(final String token) {
        tokenByDeviceId.remove(token);
    }

    public List<String> getAllNotificationTokens() {
        return List.copyOf(tokenByDeviceId.values());
    }
}
