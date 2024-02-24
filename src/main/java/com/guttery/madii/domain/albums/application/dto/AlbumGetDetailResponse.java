package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "앨범 상세 조회 응답")
public record AlbumGetDetailResponse(
        @Schema(description = "앨범 공개 여부", example = "true(1)")
        Boolean isAlbumOfficial,
        @Schema(description = "앨범 썸네일 아이콘 번호", example = "3")
        Integer albumIconNum,
        @Schema(description = "앨범 썸네일 배경 번호", example = "2")
        Integer albumColorNum,
        @Schema(description = "앨범 저장 여부", example = "true(1)")
        Boolean isAlbumSaved,
        @Schema(description = "앨범 제목", example = "겨울 필수 소확행 모음 zip")
        String name,
        @Schema(description = "앨범 생성자 닉네임", example = "구떠리님")
        String nickname,
        @Schema(description = "앨범 상세 설명", example = "겨울 필수 소확행 모음 zip 설명")
        String description,
        @Schema(description = "앨범 속 소확행 정보 리스트")
        List<JoyGetInfo> joyInfoList
) {
}
