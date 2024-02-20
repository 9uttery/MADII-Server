package com.guttery.madii.domain.user.infrastructure;

import com.guttery.madii.domain.user.domain.repository.UserTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisUserTokenStore implements UserTokenStore {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void removeToken(final String token) {
        stringRedisTemplate.delete(token);
    }
}
