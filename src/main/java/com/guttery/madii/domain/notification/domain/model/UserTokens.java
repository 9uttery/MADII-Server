package com.guttery.madii.domain.notification.domain.model;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "usertokens")
public class UserTokens {
    @Id
    @BsonProperty("_id")
    private String _id;
    private Set<String> tokens;

    public static UserTokens createEmpty(String userId) {
        return new UserTokens(userId, new HashSet<>());
    }

    public void addToken(String token) {
        tokens.add(token);
    }

    public void removeToken(String token) {
        tokens.remove(token);
    }
}
