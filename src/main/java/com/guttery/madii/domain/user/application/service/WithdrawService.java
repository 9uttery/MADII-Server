package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class WithdrawService {
    private final UserRepository userRepository;

    @Transactional
    public void withdraw(final UserPrincipal userPrincipal) {
        final User foundUser = userRepository.findById(userPrincipal.id())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_NOT_FOUND));

        userRepository.delete(foundUser);
    }
}
