package com.guttery.madii.domain.notice.infrastructure;

import com.guttery.madii.common.domain.repository.BaseQueryDslRepository;
import com.guttery.madii.domain.notice.application.dto.NoticeInfo;
import com.guttery.madii.domain.notice.application.dto.RecentNoticesResponse;
import com.guttery.madii.domain.notice.domain.model.Notice;
import com.guttery.madii.domain.notice.domain.repository.NoticeQuerydslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.guttery.madii.domain.notice.domain.model.QNotice.notice;

@Repository
public class NoticeRepositoryImpl extends BaseQueryDslRepository<Notice> implements NoticeQuerydslRepository {
    public NoticeRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public RecentNoticesResponse findRecent30DaysNotices(final LocalDateTime date) {
        final LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        final List<NoticeInfo> foundNotices = select(NoticeInfo.class, notice.noticeId, notice.title, notice.contents, notice.createdAt)
                .from(notice)
                .where(notice.createdAt.after(startOfDay.minusDays(30)))
                .orderBy(notice.createdAt.desc())
                .fetch();

        return new RecentNoticesResponse(foundNotices);
    }
}
