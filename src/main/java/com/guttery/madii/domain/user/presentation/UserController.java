package com.guttery.madii.domain.user.presentation;

import com.guttery.madii.domain.user.application.dto.KakaoLoginRequest;
import com.guttery.madii.domain.user.application.dto.NormalLoginRequest;
import com.guttery.madii.domain.user.application.dto.TokenResponse;
import com.guttery.madii.domain.user.application.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final LoginService loginService;

    @PostMapping("/login/normal")
    public TokenResponse normalLogin(@Valid @RequestBody NormalLoginRequest normalLoginRequest) {
        return loginService.normalLogin(normalLoginRequest);
    }

    @PostMapping("/login/kakao")
    public TokenResponse kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return loginService.kakaoLogin(kakaoLoginRequest);
    }
}
