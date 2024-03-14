package com.guttery.madii.domain.notice.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "공지사항 정보")
public record NoticeInfo(
        @Schema(description = "공지사항 ID", example = "1")
        Long id,
        @Schema(description = "공지사항 제목", example = "공지사항 제목")
        String title,
        @Schema(description = "공지사항 내용", example = "공지사항 내용")
        String contents,
        @Schema(description = "공지사항 작성일자", example = "2021-10-01")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

}
