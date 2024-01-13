package com.guttery.madii.domain.user.presentation;

import com.guttery.madii.domain.user.application.dto.AppleLoginRequest;
import com.guttery.madii.domain.user.application.dto.KakaoLoginRequest;
import com.guttery.madii.domain.user.application.dto.NormalLoginRequest;
import com.guttery.madii.domain.user.application.dto.SignUpRequest;
import com.guttery.madii.domain.user.application.dto.TokenResponse;
import com.guttery.madii.domain.user.application.service.LoginService;
import com.guttery.madii.domain.user.application.service.SignUpService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
@Tag(name = "User", description = "User 관련 API")
public class UserController {
    private final SignUpService signUpService;
    private final LoginService loginService;

    @GetMapping("/id-check")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ID 중복 체크 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    public boolean checkLoginId(
            @NotBlank @RequestParam final String loginId
    ) {
        return !signUpService.isDuplicatedLoginId(loginId);
    }

    @PostMapping("/sign-up")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원가입 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        signUpService.signUp(signUpRequest);
    }

    @PostMapping("/login/normal")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일반 로그인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    public TokenResponse normalLogin(@Valid @RequestBody NormalLoginRequest normalLoginRequest) {
        return loginService.normalLogin(normalLoginRequest);
    }

    @PostMapping("/login/kakao")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카카오 로그인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    public TokenResponse kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return loginService.kakaoLogin(kakaoLoginRequest);
    }

    @PostMapping("/login/apple")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "애플 로그인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    public TokenResponse appleLogin(@Valid @RequestBody AppleLoginRequest appleLoginRequest) {
        return loginService.appleLogin(appleLoginRequest);
    }


}
