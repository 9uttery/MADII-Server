package com.guttery.madii.domain.mail.application.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailVerificationCodeGenerator {
    private static final int CODE_LOWER_BOUND = 100000;
    private static final int CODE_UPPER_BOUND = 1000000;

    public static String generate() {
        return String.valueOf(RandomUtils.nextInt(CODE_LOWER_BOUND, CODE_UPPER_BOUND));
    }
}
