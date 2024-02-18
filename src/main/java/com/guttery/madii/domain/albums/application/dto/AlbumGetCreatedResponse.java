package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "내가 만든 앨범 목록 조회 응답")
public record AlbumGetCreatedResponse(
        @Schema(description = "앨범 아이디", example = "11")
        Long albumId,
        @Schema(description = "앨범 제목", example = "겨울 필수 소확행 모음 zip 설명")
        String name
) {
}
