package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.domain.user.application.dto.LogoutRequest;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import com.guttery.madii.domain.user.domain.repository.UserTokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class LogoutService {
    private final UserRepository userRepository;
    private final UserTokenStore userTokenStore;

    @Transactional
    public void logout(final LogoutRequest logoutRequest, final UserPrincipal userPrincipal) {
        UserServiceHelper.validateExistingUser(userRepository, userPrincipal);
        userTokenStore.removeToken(logoutRequest.refreshToken());
    }
}
