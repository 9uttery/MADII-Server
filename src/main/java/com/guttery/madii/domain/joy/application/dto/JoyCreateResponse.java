package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "소확행 기록 응답")
public record JoyCreateResponse(
        @Schema(description = "소확행 썸네일 아이콘 번호", example = "3")
        Integer joyIconNum,
        @Schema(description = "소확행 내용", example = "낮잠자기")
        String contents
) {
}
