package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "앨범 생성 응답")
public record AlbumCreateResponse(
        @Schema(description = "앨범 아이디", example = "1")
        Long albumId,
        @Schema(description = "앨범 제목", example = "겨울 필수 소확행 모음 zip")
        String name
) {
}
