package com.guttery.madii.domain.notification.application.service;

import com.guttery.madii.domain.notification.application.dto.NotificationInfo;
import com.guttery.madii.domain.notification.application.dto.NotificationListResponse;
import com.guttery.madii.domain.notification.application.mapper.NotificationMapper;
import com.guttery.madii.domain.notification.domain.repository.NotificationRepository;
import com.guttery.madii.domain.user.application.service.UserServiceHelper;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationQueryService {
    private static final int DATE_CRITERIA_OFFSET = 30;

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationListResponse getNotificationList(final UserPrincipal userPrincipal) {
        final User foundUser = UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final LocalDateTime dateCriteria = LocalDateTime.now().toLocalDate().atStartOfDay().minusDays(DATE_CRITERIA_OFFSET);
        final List<NotificationInfo> notificationInfos = notificationRepository.findAllByUserAndCreatedAtIsAfter(foundUser, dateCriteria)
                .stream()
                .map(NotificationMapper::toNotificationInfo)
                .toList();

        return new NotificationListResponse(notificationInfos);
    }
}
