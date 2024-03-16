package com.guttery.madii.domain.notification.application.dto;

import java.util.List;

public record FlattedNotificationTokenDto(
        String userId,
        List<String> tokens
) {
}
