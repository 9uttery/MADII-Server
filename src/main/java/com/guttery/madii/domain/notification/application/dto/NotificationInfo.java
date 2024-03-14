package com.guttery.madii.domain.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "알림 정보")
public record NotificationInfo(
        @Schema(description = "알림 이름")
        String title,
        @Schema(description = "알림 내용")
        String contents,
        @Schema(description = "알림 생성 날짜")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {
}
