package com.guttery.madii.domain.joy.infrastructure;

import com.guttery.madii.common.domain.repository.BaseQueryDslRepository;
import com.guttery.madii.domain.joy.domain.model.Joy;
import com.guttery.madii.domain.joy.domain.repository.JoyQueryDslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class JoyRepositoryImpl extends BaseQueryDslRepository<Joy> implements JoyQueryDslRepository {
    public JoyRepositoryImpl(JPAQueryFactory queryFactory) { super(queryFactory); }

}
