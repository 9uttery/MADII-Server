package com.guttery.madii.domain.achievement.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class FinishInfo {
    private Boolean isFinished;
    private LocalDateTime finishedAt;
    @Enumerated(EnumType.STRING)
    private Satisfaction satisfaction;

    public static FinishInfo createNotFinished() {
        return new FinishInfo(false, null, null);
    }

    public static FinishInfo createFinished(Satisfaction satisfaction) {
        return new FinishInfo(true, LocalDateTime.now(), satisfaction);
    }
}
