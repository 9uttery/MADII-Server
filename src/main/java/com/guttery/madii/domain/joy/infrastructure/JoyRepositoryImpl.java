package com.guttery.madii.domain.joy.infrastructure;

import com.guttery.madii.domain.joy.application.dto.JoyGetMyAllResponse;
import com.guttery.madii.domain.joy.application.dto.JoyGetMyOne;
import com.guttery.madii.domain.joy.domain.model.QJoy;
import com.guttery.madii.domain.joy.domain.repository.JoyQueryDslRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@Repository
@RequiredArgsConstructor
@Slf4j
public class JoyRepositoryImpl implements JoyQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<JoyGetMyAllResponse> getMyJoy(Long userId) {
        QJoy joy = QJoy.joy;
        QJoy joySub = new QJoy("joySub");

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})", joy.createdAt, "%Y. %m. %d"
        );
        StringTemplate formattedDateSub = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})", joySub.createdAt, "%Y. %m. %d"
        );

        return queryFactory
                .selectFrom(joy)
                .where(joy.user.userId.eq(userId))
                .orderBy(joy.createdAt.desc())
                .transform(groupBy(formattedDate)
                        .list(Projections.constructor(JoyGetMyAllResponse.class,
                                formattedDate,
                                JPAExpressions.select(joySub.count())
                                        .from(joySub)
                                        .where(joySub.user.userId.eq(userId), formattedDateSub.eq(formattedDate)),
                                list(Projections.constructor(JoyGetMyOne.class,
                                        joy.joyIconNum, joy.contents)))));
    }

}
