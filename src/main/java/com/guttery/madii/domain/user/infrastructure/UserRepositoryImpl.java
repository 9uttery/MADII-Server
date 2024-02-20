package com.guttery.madii.domain.user.infrastructure;

import com.guttery.madii.common.domain.repository.BaseQueryDslRepository;
import com.guttery.madii.domain.user.application.dto.BeforeWithdrawInfoResponse;
import com.guttery.madii.domain.user.domain.model.SocialInfo;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.repository.UserQueryDslRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.guttery.madii.domain.achievement.domain.model.QAchievement.achievement;
import static com.guttery.madii.domain.user.domain.model.QUser.user;

@Repository
public class UserRepositoryImpl extends BaseQueryDslRepository<User> implements UserQueryDslRepository {
    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public Optional<User> findUserByLoginId(final String loginId) {
        return Optional.ofNullable(
                select(user)
                        .from(user)
                        .where(user.loginInfo.loginId.eq(loginId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<User> findUserBySocialInfo(final SocialInfo socialInfo) {
        return Optional.ofNullable(
                select(user)
                        .from(user)
                        .where(user.socialInfo.eq(socialInfo))
                        .fetchOne()
        );
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        return select(user)
                .from(user)
                .where(user.loginInfo.loginId.eq(loginId))
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return select(user)
                .from(user)
                .where(user.userId.eq(userId))
                .fetchFirst() != null;
    }

    @Override
    public BeforeWithdrawInfoResponse getBeforeWithdrawInfo(Long userId, LocalDateTime date) {
        final NumberExpression<Integer> activeDays = Expressions.numberTemplate(Integer.class, "DATEDIFF({0}, {1})", date, user.createdAt).as("activeDays");

        return select(BeforeWithdrawInfoResponse.class, user.userProfile.nickname, activeDays, achievement.joy.joyId.countDistinct(), achievement.achievementId.countDistinct())
                .from(user)
                .leftJoin(achievement).on(achievement.achiever.eq(user))
                .where(user.userId.eq(userId))
                .groupBy(user.userId)
                .fetchOne();
    }


}
