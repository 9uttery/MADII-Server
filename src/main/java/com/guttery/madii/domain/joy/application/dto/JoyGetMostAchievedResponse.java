package com.guttery.madii.domain.joy.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "가장 많이 기록된 소확행 정보")
public record JoyGetMostAchievedResponse(
        @Schema(description = "가장 많이 기록된 소확행 정보 리스트")
        List<MostAchievedJoyInfo> mostAchievedJoyInfos
) {
}
