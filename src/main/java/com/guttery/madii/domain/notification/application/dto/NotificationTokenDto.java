package com.guttery.madii.domain.notification.application.dto;

import java.util.List;

public record NotificationTokenDto(
        List<String> tokensList
) {
}