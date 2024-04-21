package com.guttery.madii.domain.mail.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailVerifyService {
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional(readOnly = true)
    public void verifyEmail(final String email, final String code) {
        final String redisCode = redisTemplate.opsForValue().getAndDelete(email);
        if (redisCode == null || !redisCode.equals(code)) {
            throw CustomException.of(ErrorDetails.MAIL_VERIFY_FAILED);
        }
    }
}
