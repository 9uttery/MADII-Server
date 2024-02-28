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

@RequiredArgsConstructor
@Slf4j
@Service
public class UserTokenService {
    private final UserTokensRepository userTokensRepository;
    private final UserRepository userRepository;

    public void saveUserToken(final SaveTokenRequest saveTokenRequest, UserPrincipal userPrincipal) {
        UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final UserTokens foundUserTokens = userTokensRepository.findById(userPrincipal.id())
                .orElseGet(() -> UserTokens.createEmpty(userPrincipal.id()));

        foundUserTokens.addToken(saveTokenRequest.token());
        userTokensRepository.save(foundUserTokens);
    }

    public void deleteUserToken(final String token, UserPrincipal userPrincipal) {
        UserServiceHelper.findExistingUser(userRepository, userPrincipal);
        final UserTokens foundUserTokens = userTokensRepository.findById(userPrincipal.id())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_TOKEN_INFORMATION_NOT_EXISTS));

        foundUserTokens.removeToken(token);
        userTokensRepository.save(foundUserTokens);
    }
}
