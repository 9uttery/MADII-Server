package com.guttery.madii.common.exception;

import io.sentry.Sentry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SentryCapturingHelper {
    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";
    private static final String SENTRY_ENABLED_PROFILE = "prod";

    public static void captureException(Exception e) {
        if (isSentryEnabled()) {
            Sentry.captureException(e);
        }
    }

    private static boolean isSentryEnabled() {
        if (System.getProperty(SPRING_PROFILE_ACTIVE) == null) {
            return false;
        }

        return System.getProperty(SPRING_PROFILE_ACTIVE).equals(SENTRY_ENABLED_PROFILE);
    }

}
