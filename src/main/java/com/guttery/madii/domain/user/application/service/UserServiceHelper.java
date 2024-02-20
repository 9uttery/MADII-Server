package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserServiceHelper {
    public static User findExistingUser(final UserRepository userRepository, final UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.id())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_NOT_FOUND));
    }

    public static void validateExistingUser(final UserRepository userRepository, final UserPrincipal userPrincipal) {
        if (!userRepository.existsByUserId(userPrincipal.id())) {
            throw CustomException.of(ErrorDetails.USER_NOT_FOUND);
        }
    }
}
