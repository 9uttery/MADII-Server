package com.guttery.madii.domain.joy.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyJoy {
    private Long joyId;
    private String contents;
    private Integer joyIconNum;

    public static DailyJoy createFromJoy(Joy joy) {
        return new DailyJoy(joy.getJoyId(), joy.getContents(), joy.getJoyIconNum());
    }
}
