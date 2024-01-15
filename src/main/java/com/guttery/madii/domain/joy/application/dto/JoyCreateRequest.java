package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "소확행 기록 요청")
public record JoyCreateRequest(
        @NotBlank(message = "소확행 내용은 필수입니다.")
        @Schema(description = "소확행 내용", example = "낮잠자기")
        @Size(max=30, message = "소확행은 30자 이내로 입력해 주세요.")
        String contents
) {
}
