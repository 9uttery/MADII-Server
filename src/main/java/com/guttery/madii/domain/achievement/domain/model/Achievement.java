package com.guttery.madii.domain.achievement.domain.model;

import com.guttery.madii.common.domain.model.BaseTimeEntity;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.user.domain.model.User;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "t_achievement")
@Access(AccessType.FIELD)
public class Achievement extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User achiever;
    @ManyToOne(fetch = FetchType.LAZY)
    private Joy joy;
    @Embedded
    private FinishInfo finishInfo;

    public Achievement(User achiever, Joy joy, FinishInfo finishInfo) {
        this.achiever = achiever;
        this.joy = joy;
        this.finishInfo = finishInfo;
    }

    public static Achievement create(User user, Joy joy, FinishInfo finishInfo) {
        return new Achievement(user, joy, finishInfo);
    }

    public void finish(final Satisfaction satisfaction) {
        this.finishInfo = FinishInfo.createFinished(satisfaction);
    }

    public void rate(final Satisfaction satisfaction) {
        this.finishInfo = FinishInfo.updateSatisfaction(this.finishInfo, satisfaction);
    }

    public void cancel() {
        this.finishInfo = FinishInfo.createNotFinished();
    }

    public boolean isAchievedBy(final Long userId) {
        return this.achiever.matchesUserId(userId);
    }

    public Achievement copyForMove() {
        return new Achievement(this.achiever, this.joy, FinishInfo.createNotFinished());
    }
}
