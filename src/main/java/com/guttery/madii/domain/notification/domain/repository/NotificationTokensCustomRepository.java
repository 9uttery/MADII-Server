package com.guttery.madii.domain.notification.domain.repository;

import java.util.List;

public interface NotificationTokensCustomRepository {
    List<String> getTokensByUserIds(List<String> userIds);
}
