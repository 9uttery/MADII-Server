package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "앨범 신고 요청")
public record AlbumReportRequest(
        @Schema(description = "앨범 신고 이유", example = "부적절한 표현")
        @Size(max=250, message = "신고 이유를 250자 이내로 입력해 주세요.")
        String contents
) {
}
