package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.application.dto.ResetPasswordRequest;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class LoginInfoService {
    private final UserRepository userRepository;

    public void changePassword(final ResetPasswordRequest resetPasswordRequest) {
        final User user = UserServiceHelper.findExistingUserByEmail(userRepository, resetPasswordRequest.email());
        if (!user.hasLoginInfo()) {
            throw CustomException.of(ErrorDetails.USER_HAS_NO_LOGIN_INFO);
        }
        user.changePassword(resetPasswordRequest.password());
        userRepository.save(user);
    }
}
