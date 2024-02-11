package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "취향저격 소확행 조회 요청")
public record JoyGetRecommendRequest(
        @Schema(description = "WHEN 태그", example = "[1,2]")
        List<Long> when,
        @Schema(description = "WHO 태그", example = "[4]")
        List<Long> who,
        @Schema(description = "WHICH 태그", example = "[8]")
        List<Long> which
) {
}
