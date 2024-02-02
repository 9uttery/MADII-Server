package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "특정 소확행 저장 여부와 함께 앨범 목록 조회 응답")
public record AlbumGetJoyAllResponse(
        @Schema(description = "앨범에 소확행 포함 여부", example = "true(1)")
        Boolean isSaved,
        @Schema(description = "앨범 아이디", example = "11")
        Long albumId,
        @Schema(description = "앨범 썸네일 아이콘 번호", example = "3")
        Integer joyIconNum,
        @Schema(description = "앨범 썸네일 배경 번호", example = "2")
        Integer albumColorNum,
        @Schema(description = "앨범 제목", example = "겨울 필수 소확행 모음 zip 설명")
        String name,
        @Schema(description = "마지막 수정 날짜", example = "2024. 01. 12")
        LocalDateTime modifiedAt
) {
}
