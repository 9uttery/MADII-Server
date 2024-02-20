package com.guttery.madii.domain.notice.application.service;

import com.guttery.madii.domain.notice.application.dto.CreateNoticeRequest;
import com.guttery.madii.domain.notice.application.dto.RecentNoticesResponse;
import com.guttery.madii.domain.notice.domain.model.Notice;
import com.guttery.madii.domain.notice.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public RecentNoticesResponse getRecentNotices() {
        return noticeRepository.findRecent30DaysNotices(LocalDateTime.now());
    }

    public void createNotice(final CreateNoticeRequest createNoticeRequest) {
        final Notice newNotice = Notice.create(createNoticeRequest.title(), createNoticeRequest.contents());
        noticeRepository.save(newNotice);
    }
}
