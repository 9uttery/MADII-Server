package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.user.application.dto.SignUpRequest;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SignUpService {
    private final UserRepository userRepository;

    public void signUp(final SignUpRequest signUpRequest) {
        if (isDuplicatedLoginId(signUpRequest.loginId())) {
            throw CustomException.of(ErrorDetails.DUPLICATED_LOGIN_ID);
        }

        final User user = User.createNormalUser(signUpRequest.loginId(), signUpRequest.password(), signUpRequest.agreesMarketing());

        userRepository.save(user);
    }

    public boolean isDuplicatedLoginId(final String loginId) {
        return userRepository.existsByLoginId(loginId);
    }
}
