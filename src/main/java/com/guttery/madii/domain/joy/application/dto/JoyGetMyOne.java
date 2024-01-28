package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record JoyGetMyOne(
        @Schema(description = "소확행 아이디", example = "11")
        Long joyId,
        @Schema(description = "소확행 썸네일 아이콘 번호", example = "3")
        Integer joyIconNum,
        @Schema(description = "소확행 내용", example = "낮잠자기")
        String contents
) {
}
