package com.guttery.madii.domain.joy.application.dto;

import com.guttery.madii.domain.joy.domain.model.RecommendedJoys;

public record JoyGetTodayResponse(
        String joyId,
        String contents,
        String joyIconNum
) {
    public static JoyGetTodayResponse of(RecommendedJoys.DailyJoy dailyJoy) {
        return new JoyGetTodayResponse(dailyJoy.getKey(), dailyJoy.getContents(), dailyJoy.getJoyIconNum());
    }
}
