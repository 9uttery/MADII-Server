package com.guttery.madii.domain.achievement.infrastructure;

import com.guttery.madii.common.domain.repository.BaseQueryDslRepository;
import com.guttery.madii.domain.achievement.domain.repository.AchievementQueryDslRepository;
import com.guttery.madii.domain.achievement.domain.repository.AchievementRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AchievementRepositoryImpl extends BaseQueryDslRepository<AchievementRepository> implements AchievementQueryDslRepository {
    public AchievementRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }
}
