package com.guttery.madii.domain.notification.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "알림 리스트 응답")
public record NotificationListResponse(
        @Schema(description = "알림 리스트")
        List<NotificationInfo> notificationInfos
) {
}
