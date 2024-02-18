package com.guttery.madii.domain.achievement.infrastructure;

import com.guttery.madii.common.domain.repository.BaseQueryDslRepository;
import com.guttery.madii.domain.achievement.application.dto.CalenderAchievementColorResponse;
import com.guttery.madii.domain.achievement.application.dto.CalenderDailyJoyAchievementResponse;
import com.guttery.madii.domain.achievement.application.dto.DailyAchievementColorInfos;
import com.guttery.madii.domain.achievement.application.dto.DailyJoyAchievementInfo;
import com.guttery.madii.domain.achievement.application.dto.DailyJoyPlaylist;
import com.guttery.madii.domain.achievement.application.dto.JoyAchievementInfo;
import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;
import com.guttery.madii.domain.achievement.domain.repository.AchievementQueryDslRepository;
import com.guttery.madii.domain.achievement.domain.repository.AchievementRepository;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.guttery.madii.domain.achievement.domain.model.QAchievement.achievement;
import static com.guttery.madii.domain.joy.domain.model.QJoy.joy;
import static com.guttery.madii.domain.user.domain.model.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;


@Repository
public class AchievementRepositoryImpl extends BaseQueryDslRepository<AchievementRepository> implements AchievementQueryDslRepository {
    private static final int DAILY_ACHIEVEMENT_COLOR_INFO_MAX_NUM = 9;
    private static final int MAX_COLOR_NUM = 6;
    private static final int COLOR_CATEGORY_OFFSET = 1;

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
                .orderBy(achievement.finishInfo.isFinished.asc(), achievement.createdAt.asc())
                .fetch();
    }

    @Override
    public CalenderAchievementColorResponse getAchievementsInCalender(final Long userId, final LocalDate startDate, final LocalDate endDate) {
        final List<DailyAchievementColorInfos> achievementColorInfos = queryAchievementColorInfos(userId, startDate, endDate);

        return new CalenderAchievementColorResponse(achievementColorInfos);
    }

    private List<DailyAchievementColorInfos> queryAchievementColorInfos(final Long userId, final LocalDate startDate, final LocalDate endDate) {
        final LocalDateTime startOfDay = startDate.atStartOfDay();
        final LocalDateTime endOfDay = endDate.atStartOfDay().plusDays(1).minusSeconds(1);
        final StringExpression finishedAtToDate = stringTemplate("DATE_FORMAT({0}, {1})", achievement.finishInfo.finishedAt, "%Y-%m-%d");
        final NumberExpression<Integer> calculatedJoyIconNum = joy.joyIconNum.divide(MAX_COLOR_NUM).add(COLOR_CATEGORY_OFFSET);

        return select(achievement)
                .from(achievement)
                .join(achievement.joy, joy)
                .join(achievement.achiever, user)
                .where(achievement.achiever.userId.eq(userId), achievement.finishInfo.isFinished.isTrue(), achievement.finishInfo.finishedAt.between(startOfDay, endOfDay))
                .groupBy(finishedAtToDate)
                .limit(DAILY_ACHIEVEMENT_COLOR_INFO_MAX_NUM)
                .orderBy(achievement.finishInfo.finishedAt.asc())
                .transform(
                        groupBy(finishedAtToDate).list(
                                Projections.constructor(DailyAchievementColorInfos.class,
                                        finishedAtToDate,
                                        GroupBy.list(calculatedJoyIconNum)
                                )
                        )
                );
    }


    @Override
    public CalenderDailyJoyAchievementResponse getDailyJoyAchievementInfos(final Long userId, final LocalDate date) {
        final LocalDateTime startOfDay = date.atStartOfDay();
        final LocalDateTime endOfDay = date.atStartOfDay().plusDays(1).minusSeconds(1);

        final List<DailyJoyAchievementInfo> dailyJoyAchievementInfos = select(DailyJoyAchievementInfo.class, joy.joyId, achievement.achievementId, joy.joyIconNum, joy.contents, achievement.finishInfo.satisfaction)
                .from(achievement)
                .join(achievement.joy, joy)
                .join(achievement.achiever, user)
                .where(achievement.achiever.userId.eq(userId), achievement.createdAt.between(startOfDay, endOfDay), achievement.finishInfo.isFinished.isTrue())
                .fetch();

        return new CalenderDailyJoyAchievementResponse(dailyJoyAchievementInfos);
    }
}
