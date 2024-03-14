package com.guttery.madii.domain.notification.domain.model;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "usertokens")
public class UserTokens {
    @Id
    @BsonProperty("_id")
    private String id;
    private Map<String, String> tokenByDeviceId;

    public static UserTokens createEmpty(String userId) {
        return new UserTokens(userId, new HashMap<>());
    }

    public void addToken(final String deviceId, final String token) {
        tokenByDeviceId.put(deviceId, token);
    }

    public void removeToken(final String token) {
        tokenByDeviceId.remove(token);
    }

    public List<String> getAllUserTokens() {
        return List.copyOf(tokenByDeviceId.values());
    }
}
