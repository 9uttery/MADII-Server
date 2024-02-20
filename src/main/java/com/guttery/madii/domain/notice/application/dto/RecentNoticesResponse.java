package com.guttery.madii.domain.notice.application.dto;

import java.util.List;

public record RecentNoticesResponse(
        List<NoticeInfo> notices
) {
}
