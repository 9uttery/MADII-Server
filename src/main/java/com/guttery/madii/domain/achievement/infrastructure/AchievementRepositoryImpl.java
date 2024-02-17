package com.guttery.madii.domain.achievement.infrastructure;

import com.guttery.madii.common.domain.repository.BaseQueryDslRepository;
import com.guttery.madii.domain.achievement.application.dto.DailyJoyPlaylist;
import com.guttery.madii.domain.achievement.application.dto.JoyAchievementInfo;
import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;
import com.guttery.madii.domain.achievement.domain.repository.AchievementQueryDslRepository;
import com.guttery.madii.domain.achievement.domain.repository.AchievementRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.guttery.madii.domain.achievement.domain.model.QAchievement.achievement;
import static com.guttery.madii.domain.joy.domain.model.QJoy.joy;
import static com.guttery.madii.domain.user.domain.model.QUser.user;


@Repository
public class AchievementRepositoryImpl extends BaseQueryDslRepository<AchievementRepository> implements AchievementQueryDslRepository {
    public AchievementRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public JoyPlaylistResponse getAchievementsInPlaylist(final Long userId, final LocalDate date) {
        final List<JoyAchievementInfo> yesterdayAchievementInfos = queryJoyAchievementInfos(userId, date.minusDays(1));
        final List<JoyAchievementInfo> todayAchievementInfos = queryJoyAchievementInfos(userId, date);

        return new JoyPlaylistResponse(
                new DailyJoyPlaylist(date, todayAchievementInfos),
                new DailyJoyPlaylist(date.minusDays(1), yesterdayAchievementInfos)
        );
    }

    private List<JoyAchievementInfo> queryJoyAchievementInfos(final Long userId, final LocalDate date) {
        final LocalDateTime startOfDay = date.atStartOfDay();
        final LocalDateTime endOfDay = date.atStartOfDay().plusDays(1).minusSeconds(1);

        return select(JoyAchievementInfo.class, joy.joyId, achievement.achievementId, joy.joyIconNum, joy.contents, achievement.finishInfo.isFinished, achievement.finishInfo.satisfaction)
                .from(achievement)
                .join(achievement.joy, joy)
                .join(achievement.achiever, user)
                .where(achievement.achiever.userId.eq(userId), achievement.createdAt.between(startOfDay, endOfDay))
                .orderBy(achievement.finishInfo.isFinished.asc(), achievement.createdAt.desc())
                .fetch();
    }
}
