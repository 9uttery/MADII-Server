package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.common.jwt.JwtProvider;
import com.guttery.madii.domain.user.application.dto.KakaoLoginRequest;
import com.guttery.madii.domain.user.application.dto.NormalLoginRequest;
import com.guttery.madii.domain.user.application.dto.OidcDecodePayload;
import com.guttery.madii.domain.user.application.dto.TokenResponse;
import com.guttery.madii.domain.user.domain.model.SocialInfo;
import com.guttery.madii.domain.user.domain.model.SocialProvider;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import com.guttery.madii.domain.user.domain.service.IdTokenDecodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LoginService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IdTokenDecodeService idTokenDecodeService;

    public TokenResponse normalLogin(final NormalLoginRequest normalLoginRequest) {
        final User user = userRepository.findUserByLoginId(normalLoginRequest.loginId())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_NOT_FOUND));

//        if (!bCryptPasswordEncoder.matches(normalLoginRequest.password(), user.getEncryptedPassword())) {
//            throw CustomException.of(ErrorDetails.USER_NOT_FOUND);
//        }

        return new TokenResponse(jwtProvider.generateAccessToken(user.getUserId(), user.getRole()), jwtProvider.generateRefreshToken(user.getUserId(), user.getRole()));
    }

    public TokenResponse kakaoLogin(final KakaoLoginRequest kakaoLoginRequest) {
        // ID 토큰으로 찾아온 유저 정보
        final OidcDecodePayload oidcDecodePayload = idTokenDecodeService.getPayloadFromIdToken(kakaoLoginRequest.idToken());

        final User user = userRepository.findUserBySocialInfo(new SocialInfo(oidcDecodePayload.sub(), SocialProvider.KAKAO))
                .orElseGet(() -> createNewKakaoUser(oidcDecodePayload));

        return new TokenResponse(jwtProvider.generateAccessToken(user.getUserId(), user.getRole()), jwtProvider.generateRefreshToken(user.getUserId(), user.getRole()));
    }

    private User createNewKakaoUser(final OidcDecodePayload oidcDecodePayload) {
        final User newUser = User.createSocialUser(oidcDecodePayload.sub(), SocialProvider.KAKAO);
        // Payload 정보로 UserProfile 업데이트하는 로직 필요 (카카오 기본 닉네임, 프사 가져와야 함!)

        return userRepository.save(newUser);
    }
}
