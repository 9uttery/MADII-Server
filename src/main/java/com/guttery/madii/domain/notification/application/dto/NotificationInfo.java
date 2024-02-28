package com.guttery.madii.domain.notification.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "알림 정보")
public record NotificationInfo(
        @Schema(description = "알림 이름")
        String title,
        @Schema(description = "알림 내용")
        String contents,
        @Schema(description = "알림 생성 날짜")
        String createdAt
) {
}
