package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 조회 응답")
public record ProfileReadResponse(
        @Schema(description = "유저 ID", example = "1")
        Long id,
        @Schema(description = "닉네임", example = "테스트")
        String nickname,
        @Schema(description = "프로필 이미지", example = "https://avatars.githubusercontent.com/u/12345678?v=4")
        String image
) {
}
