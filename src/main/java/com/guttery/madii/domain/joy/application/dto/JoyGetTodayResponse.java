package com.guttery.madii.domain.joy.application.dto;

import com.guttery.madii.domain.joy.domain.model.DailyJoy;

public record JoyGetTodayResponse(
        Long joyId,
        String contents,
        Integer joyIconNum
) {
    public static JoyGetTodayResponse of(DailyJoy dailyJoy) {
        return new JoyGetTodayResponse(dailyJoy.getJoyId(), dailyJoy.getContents(), dailyJoy.getJoyIconNum());
    }
}
