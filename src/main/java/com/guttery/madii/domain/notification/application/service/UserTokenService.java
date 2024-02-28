package com.guttery.madii.domain.notification.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.notification.application.dto.SaveTokenRequest;
import com.guttery.madii.domain.notification.domain.model.UserTokens;
import com.guttery.madii.domain.notification.domain.repository.UserTokensRepository;
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
public class UserTokenService {
    private final UserTokensRepository userTokensRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveUserToken(final SaveTokenRequest saveTokenRequest, final UserPrincipal userPrincipal) {
        UserServiceHelper.findExistingUser(userRepository, userPrincipal);

        final UserTokens foundUserTokens = userTokensRepository.findById(userPrincipal.id().toString())
                .orElseGet(() -> UserTokens.createEmpty(userPrincipal.id().toString()));

        foundUserTokens.addToken(saveTokenRequest.token());
        userTokensRepository.save(foundUserTokens);
    }

    @Transactional
    public void deleteUserToken(final String token, final UserPrincipal userPrincipal) {
        UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final UserTokens foundUserTokens = userTokensRepository.findById(userPrincipal.id().toString())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_TOKEN_INFORMATION_NOT_EXISTS));

        foundUserTokens.removeToken(token);
        userTokensRepository.save(foundUserTokens);
    }
}
