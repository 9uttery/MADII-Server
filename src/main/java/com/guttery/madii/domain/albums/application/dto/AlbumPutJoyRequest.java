package com.guttery.madii.domain.albums.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "앨범 이름, 설명 수정 요청")
public record AlbumPutJoyRequest(
        @NotBlank(message = "앨범 제목은 필수입니다.")
        @Schema(description = "앨범 제목", example = "겨울 필수 소확행 모음 zip")
        @Size(max=30, message = "앨범 제목은 30자 이내로 입력해 주세요.")
        String name,
        @Schema(description = "앨범 내용", example = "이 소확행은 기분이 째질 때 츄라이해보면 좋은 소확행이에요.")
        @Size(max=50, message = "앨범 내용은 50자 이내로 입력해 주세요.")
        String description
) {
}
