package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "앨범에 소확행 추가")
public record AlbumSaveJoyRequest(
        @Schema(description = "앨범 아이디 목록", example = "{1,2}")
        List<Long> albumIds
) {
}
