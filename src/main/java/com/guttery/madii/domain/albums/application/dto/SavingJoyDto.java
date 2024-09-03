package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SavingJoyDto(
        @Schema(description = "소확행 아이디", example = "11")
        Long joyId,
        @Schema(description = "소확행 내용", example = "낮잠자기")
        String contents,
        @Schema(description = "소확행 정렬 순서", example = "3")
        Integer joyOrder
) {
}
