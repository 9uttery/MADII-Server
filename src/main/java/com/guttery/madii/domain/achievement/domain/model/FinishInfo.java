package com.guttery.madii.domain.achievement.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public static FinishInfo updateSatisfaction(FinishInfo finishInfo, Satisfaction satisfaction) {
        return new FinishInfo(finishInfo.isFinished, finishInfo.finishedAt, satisfaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinishInfo that = (FinishInfo) o;
        return Objects.equals(isFinished, that.isFinished) && Objects.equals(finishedAt, that.finishedAt) && satisfaction == that.satisfaction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFinished, finishedAt, satisfaction);
    }
}
