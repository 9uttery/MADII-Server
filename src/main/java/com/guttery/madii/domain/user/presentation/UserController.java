package com.guttery.madii.domain.user.presentation;

import com.guttery.madii.domain.user.application.dto.KakaoLoginRequest;
import com.guttery.madii.domain.user.application.dto.NormalLoginRequest;
import com.guttery.madii.domain.user.application.dto.SignUpRequest;
import com.guttery.madii.domain.user.application.dto.TokenResponse;
import com.guttery.madii.domain.user.application.service.LoginService;
import com.guttery.madii.domain.user.application.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final SignUpService signUpService;
    private final LoginService loginService;

    @GetMapping("/id-check")
    public boolean checkLoginId(final String loginId) {
        return !signUpService.isDuplicatedLoginId(loginId);
    }

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        signUpService.signUp(signUpRequest);
    }

    @PostMapping("/login/normal")
    public TokenResponse normalLogin(@Valid @RequestBody NormalLoginRequest normalLoginRequest) {
        return loginService.normalLogin(normalLoginRequest);
    }

    @PostMapping("/login/kakao")
    public TokenResponse kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return loginService.kakaoLogin(kakaoLoginRequest);
    }
}
