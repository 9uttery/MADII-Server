package com.guttery.madii.domain.user.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.common.jwt.JwtProvider;
import com.guttery.madii.domain.user.application.dto.AppleLoginRequest;
import com.guttery.madii.domain.user.application.dto.KakaoLoginRequest;
import com.guttery.madii.domain.user.application.dto.NormalLoginRequest;
import com.guttery.madii.domain.user.application.dto.OidcDecodePayload;
import com.guttery.madii.domain.user.application.dto.TokenResponse;
import com.guttery.madii.domain.user.domain.model.SocialInfo;
import com.guttery.madii.domain.user.domain.model.SocialProvider;
import com.guttery.madii.domain.user.domain.model.User;
import com.guttery.madii.domain.user.domain.repository.UserRepository;
import com.guttery.madii.domain.user.domain.service.AppleIdTokenDecodeService;
import com.guttery.madii.domain.user.domain.service.KakaoIdTokenDecodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class LoginService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KakaoIdTokenDecodeService kakaoIdTokenDecodeService;
    private final AppleIdTokenDecodeService appleIdTokenDecodeService;

    @Transactional(readOnly = true)
    public TokenResponse normalLogin(final NormalLoginRequest normalLoginRequest) {
        final User user = userRepository.findUserByLoginId(normalLoginRequest.loginId())
                .orElseThrow(() -> CustomException.of(ErrorDetails.USER_NOT_FOUND));

//        if (!bCryptPasswordEncoder.matches(normalLoginRequest.password(), user.getEncryptedPassword())) {
//            throw CustomException.of(ErrorDetails.USER_NOT_FOUND);
//        }

        return new TokenResponse(jwtProvider.generateAccessToken(user.getUserId(), user.getRole()), jwtProvider.generateRefreshToken(user.getUserId(), user.getRole()));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TokenResponse kakaoLogin(final KakaoLoginRequest kakaoLoginRequest) {
        // ID 토큰으로 찾아온 유저 정보
        final OidcDecodePayload oidcDecodePayload = kakaoIdTokenDecodeService.getPayloadFromIdToken(kakaoLoginRequest.idToken());

        final User user = userRepository.findUserBySocialInfo(new SocialInfo(oidcDecodePayload.sub(), SocialProvider.KAKAO))
                .orElseGet(() -> createNewKakaoUser(oidcDecodePayload));

        return new TokenResponse(jwtProvider.generateAccessToken(user.getUserId(), user.getRole()), jwtProvider.generateRefreshToken(user.getUserId(), user.getRole()));
    }

    private User createNewKakaoUser(final OidcDecodePayload oidcDecodePayload) {
        final User newUser = User.createSocialUser(oidcDecodePayload.sub(), SocialProvider.KAKAO);

        newUser.updateUserProfile(oidcDecodePayload.nickname(), oidcDecodePayload.picture());

        return userRepository.save(newUser);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TokenResponse appleLogin(final AppleLoginRequest appleLoginRequest) {
        // ID 토큰으로 찾아온 유저 정보
        final OidcDecodePayload oidcDecodePayload = appleIdTokenDecodeService.getPayloadFromIdToken(appleLoginRequest.idToken());

        final User user = userRepository.findUserBySocialInfo(new SocialInfo(oidcDecodePayload.sub(), SocialProvider.APPLE))
                .orElseGet(() -> createNewAppleUser(oidcDecodePayload));

        return new TokenResponse(jwtProvider.generateAccessToken(user.getUserId(), user.getRole()), jwtProvider.generateRefreshToken(user.getUserId(), user.getRole()));
    }

    private User createNewAppleUser(final OidcDecodePayload oidcDecodePayload) {
        final User newUser = User.createSocialUser(oidcDecodePayload.sub(), SocialProvider.APPLE);

        return userRepository.save(newUser);
    }
}
