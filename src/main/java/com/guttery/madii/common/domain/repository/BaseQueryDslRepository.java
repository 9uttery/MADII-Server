package com.guttery.madii.common.domain.repository;

import com.guttery.madii.common.response.Pagination;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseQueryDslRepository<E> {
    private final JPAQueryFactory queryFactory;

    protected <T> JPAQuery<T> select(Expression<T> expr) {
        return queryFactory.select(expr);
    }

    protected <T> JPAQuery<T> select(Class<T> clazz, Expression<?>... exprs) {
        return queryFactory.select(Projections.constructor(clazz, exprs));
    }

    protected JPAQuery<E> selectFrom(EntityPath<E> from) {
        return queryFactory.selectFrom(from);
    }


    protected <T> Pagination<T> applyPagination(JPAQuery<T> contentQuery, long totalCount, Pageable pageable) {
        List<T> result = this.pagingFetch(contentQuery, pageable);

        return new Pagination<>(result, totalCount, pageable);
    }

    private <T> List<T> pagingFetch(JPAQuery<T> query, Pageable pageable) {
        if (pageable.isUnpaged()) {
            return query.fetch();
        }

        return query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
    }
}
