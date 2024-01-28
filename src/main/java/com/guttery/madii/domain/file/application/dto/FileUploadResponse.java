package com.guttery.madii.domain.file.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record FileUploadResponse(
        @Schema(description = "파일 URL", example = "https://cdn.madii.kr/%E1%84%80%E1%85%AE%E1%84%84%E1%85%A5%E1%84%85%E1%85%B5+%E1%84%85%E1%85%A9%E1%84%80%E1%85%A91.png",
                defaultValue = "https://cdn.madii.kr/%E1%84%80%E1%85%AE%E1%84%84%E1%85%A5%E1%84%85%E1%85%B5+%E1%84%85%E1%85%A9%E1%84%80%E1%85%A91.png")
        String url
) {
}
