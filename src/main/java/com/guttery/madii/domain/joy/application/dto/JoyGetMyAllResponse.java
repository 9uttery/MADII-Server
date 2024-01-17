package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record JoyGetMyAllResponse(
        @Schema(description = "소확행 기록 날짜", example = "2024. 01. 12")
        String createdAt,
        @Schema(description = "해당 날짜에 기록한 소확행 개수")
        Long size,
        @Schema(description = "소확행 목록")
        List<JoyGetMyOne> joyList
) {
}
