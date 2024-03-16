package com.guttery.madii.domain.user.infrastructure;

import com.guttery.madii.domain.user.domain.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisUserTokenRepository implements UserTokenRepository {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void removeToken(final String token) {
        stringRedisTemplate.delete(token);
    }
}
