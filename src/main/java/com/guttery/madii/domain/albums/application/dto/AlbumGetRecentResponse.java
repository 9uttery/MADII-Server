package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "최근 본 소확행 앨범 조회 응답")
public record AlbumGetRecentResponse(
        @Schema(description = "앨범 아이디", example = "11")
        Long albumId,
        @Schema(description = "앨범 썸네일 아이콘 번호", example = "3")
        Integer joyIconNum,
        @Schema(description = "앨범 썸네일 배경 번호", example = "2")
        Integer albumColorNum,
        @Schema(description = "앨범 제목", example = "겨울 필수 소확행 모음 zip 설명")
        String name
) {
}
