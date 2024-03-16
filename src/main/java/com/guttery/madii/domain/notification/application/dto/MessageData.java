package com.guttery.madii.domain.notification.application.dto;

public record MessageData(
        String title,
        String contents,
        String token
) {
}
