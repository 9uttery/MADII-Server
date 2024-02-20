package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가장 많이 기록된 소확행 정보")
public record MostAchievedJoyInfo(
        @Schema(description = "소확행 아이콘 번호", example = "1")
        Integer joyIconNum,
        @Schema(description = "소확행 내용", example = "붕어방 먹기")
        String contents,
        @Schema(description = "소확행 기록 횟수", example = "10")
        Integer achieveCount,
        @Schema(description = "많이 기록된 소확행 순위", example = "1")
        Integer rank
) {
    public static MostAchievedJoyInfo fromProjection(MostAchievedJoyInfoProjection projection) {
        return new MostAchievedJoyInfo(
                projection.getJoyIconNum(),
                projection.getContents(),
                projection.getAchieveCount(),
                projection.getAchieveRank()
        );
    }
}
