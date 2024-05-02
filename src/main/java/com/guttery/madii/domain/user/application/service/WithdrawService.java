package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.albums.domain.repository.AlbumRepository;
import com.guttery.madii.domain.joy.domain.repository.JoyRepository;
import com.guttery.madii.domain.user.application.dto.BeforeWithdrawInfoResponse;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class WithdrawService {
    private final UserRepository userRepository;
    private final JoyRepository joyRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public void withdraw(final UserPrincipal userPrincipal) {
        final User foundUser = userRepository.findById(userPrincipal.id())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_NOT_FOUND));

        albumRepository.deleteByUser(foundUser);

        // 명시적으로 모든 연관 엔티티 삭제
        joyRepository.deleteByUser(foundUser);

        // User 삭제
        userRepository.delete(foundUser);
    }

    @Transactional(readOnly = true)
    public BeforeWithdrawInfoResponse getBeforeWithdrawInfo(final UserPrincipal userPrincipal) {
        final User foundUser = userRepository.findById(userPrincipal.id())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_NOT_FOUND));

        return userRepository.getBeforeWithdrawInfo(foundUser.getUserId(), LocalDateTime.now());
    }
}
