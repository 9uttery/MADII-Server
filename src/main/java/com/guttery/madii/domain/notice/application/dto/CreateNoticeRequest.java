package com.guttery.madii.domain.notice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "공지사항 생성 요청")
public record CreateNoticeRequest(
        @NotBlank
        @Schema(description = "공지사항 제목", example = "공지사항 제목")
        String title,
        @NotBlank
        @Schema(description = "공지사항 내용", example = "공지사항 내용")
        String contents
) {
}
