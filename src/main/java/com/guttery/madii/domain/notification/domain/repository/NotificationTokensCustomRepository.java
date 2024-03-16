package com.guttery.madii.domain.notification.domain.repository;

import com.guttery.madii.domain.notification.application.dto.FlattedNotificationTokenDto;

import java.util.List;

public interface NotificationTokensCustomRepository {
    List<String> getTokensByUserIds(List<String> userIds);

    List<FlattedNotificationTokenDto> getAllFlatted();
}
