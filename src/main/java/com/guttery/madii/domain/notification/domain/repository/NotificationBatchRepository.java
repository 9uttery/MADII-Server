package com.guttery.madii.domain.notification.domain.repository;


import com.guttery.madii.domain.notification.domain.model.Notification;

import java.util.List;

public interface NotificationBatchRepository {
    void bulkSave(List<Notification> notifications);
}
