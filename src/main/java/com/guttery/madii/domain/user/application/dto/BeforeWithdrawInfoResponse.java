package com.guttery.madii.domain.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "탈퇴 전 사용자 정보 응답")
public record BeforeWithdrawInfoResponse(
        @Schema(description = "닉네임", example = "떠리떠리")
        String nickname,
        @Schema(description = "활동 기간", example = "96")
        Integer activeDays,
        @Schema(description = "실천한 소확행 수", example = "32")
        Long achievedJoyCount,
        @Schema(description = "소확행 실천 횟수", example = "231")
        Long achievementCount
) {
}
