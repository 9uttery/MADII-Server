package com.guttery.madii.domain.achievement.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyPlaylist {
    private LocalDateTime date;
    private List<JoyInfo> unfinishedJoyInfos;
    private List<JoyInfo> finishedJoyInfos;

    public static DailyPlaylist createEmptyDailyPlaylist(LocalDateTime date) {
        return new DailyPlaylist(date, new ArrayList<>(), new ArrayList<>());
    }
}
