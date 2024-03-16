package com.guttery.madii.domain.notification.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.notification.application.dto.SaveTokenRequest;
import com.guttery.madii.domain.notification.domain.model.NotificationTokens;
import com.guttery.madii.domain.notification.domain.repository.NotificationTokensRepository;
import com.guttery.madii.domain.user.application.service.UserServiceHelper;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationTokenService {
    private final NotificationTokensRepository notificationTokensRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveNotificationToken(final SaveTokenRequest saveTokenRequest, final UserPrincipal userPrincipal) {
        UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        final NotificationTokens foundNotificationTokens = notificationTokensRepository.findById(userPrincipal.id().toString())
                .orElseGet(() -> NotificationTokens.createEmpty(userPrincipal.id().toString()));

        foundNotificationTokens.addToken(saveTokenRequest.deviceId(), saveTokenRequest.token());
        notificationTokensRepository.save(foundNotificationTokens);
    }

    @Transactional
    public void deleteNotificationToken(final String token, final UserPrincipal userPrincipal) {
        UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final NotificationTokens foundNotificationTokens = notificationTokensRepository.findById(userPrincipal.id().toString())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_TOKEN_INFORMATION_NOT_EXISTS));

        foundNotificationTokens.removeToken(token);
        notificationTokensRepository.save(foundNotificationTokens);
    }
}
