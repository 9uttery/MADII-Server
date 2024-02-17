package com.guttery.madii.domain.achievement.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "어제와 오늘의 소확행 플레이리스트 응답")
public record JoyPlaylistResponse(
        @Schema(description = "오늘의 소확행 플레이리스트")
        DailyJoyPlaylist todayJoyPlayList,
        @Schema(description = "어제의 소확행 플레이리스트")
        DailyJoyPlaylist yesterdayJoyPlayList
) {
}
