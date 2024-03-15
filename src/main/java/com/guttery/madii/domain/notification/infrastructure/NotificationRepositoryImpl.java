package com.guttery.madii.domain.notification.infrastructure;

import com.guttery.madii.domain.notification.domain.model.Notification;
import com.guttery.madii.domain.notification.domain.repository.NotificationBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class NotificationRepositoryImpl implements NotificationBatchRepository {
    private static final int BATCH_SIZE = 1000;
    private static final String BATCH_INSERT_QUERY =
            "INSERT INTO t_notification (user_user_id, title, contents, is_checked, created_at) VALUES (?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void bulkSave(final List<Notification> notifications) {
        batchInsert(notifications);
    }

    private void batchInsert(final List<Notification> notifications) {
        jdbcTemplate.batchUpdate(
                BATCH_INSERT_QUERY,
                notifications,
                BATCH_SIZE,
                (PreparedStatement ps, Notification notification) -> {
                    ps.setLong(1, notification.getUserId());
                    ps.setString(2, notification.getTitle());
                    ps.setString(3, notification.getContents());
                    ps.setBoolean(4, notification.getIsChecked());
                    ps.setTimestamp(5, Timestamp.valueOf(notification.getCreatedAt()));
                }
        );
    }
}
