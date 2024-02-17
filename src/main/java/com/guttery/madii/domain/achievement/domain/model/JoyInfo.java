package com.guttery.madii.domain.achievement.domain.model;

import com.guttery.madii.domain.joy.domain.model.Joy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JoyInfo {
    private Long joyId;
    private String contents;
    private Integer joyIconNum;

    public static JoyInfo createFromJoy(Joy joy) {
        return new JoyInfo(joy.getJoyId(), joy.getContents(), joy.getJoyIconNum());
    }
}
