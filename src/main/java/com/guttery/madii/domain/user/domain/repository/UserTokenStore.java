package com.guttery.madii.domain.user.domain.repository;

public interface UserTokenStore {
    void removeToken(String token);
}
