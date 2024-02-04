package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "소확행 수정 요청")
public record JoyPutRequest(
        @NotBlank(message = "소확행 내용은 필수입니다.")
        @Schema(description = "소확행 내용", example = "낮잠자기")
        @Size(max=30, message = "소확행은 30자 이내로 입력해 주세요.")
        String contents,
        @Schema(description = "수정 전 - 소확행 포함 앨범 목록")
        List<Long> beforeAlbumIds,
        @Schema(description = "수정 후 - 소확행 포함 앨범 목록")
        List<Long> afterAlbumIds
) {
}
