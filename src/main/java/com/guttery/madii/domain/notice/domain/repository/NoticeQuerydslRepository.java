package com.guttery.madii.domain.notice.domain.repository;

import com.guttery.madii.domain.notice.application.dto.RecentNoticesResponse;

import java.time.LocalDateTime;

public interface NoticeQuerydslRepository {
    RecentNoticesResponse findRecent30DaysNotices(LocalDateTime date);
}
