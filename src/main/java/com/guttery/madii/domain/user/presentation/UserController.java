package com.guttery.madii.domain.user.presentation;

import com.guttery.madii.domain.user.application.dto.AppleLoginRequest;
import com.guttery.madii.domain.user.application.dto.KakaoLoginRequest;
import com.guttery.madii.domain.user.application.dto.LoginResponse;
import com.guttery.madii.domain.user.application.dto.LogoutRequest;
import com.guttery.madii.domain.user.application.dto.NormalLoginRequest;
import com.guttery.madii.domain.user.application.dto.ProfileReadResponse;
import com.guttery.madii.domain.user.application.dto.ProfileUpdateRequest;
import com.guttery.madii.domain.user.application.dto.RefreshResponse;
import com.guttery.madii.domain.user.application.dto.SignUpRequest;
import com.guttery.madii.domain.user.application.dto.TokenRefreshRequest;
import com.guttery.madii.domain.user.application.dto.UpdateMarketingAgreementRequest;
import com.guttery.madii.domain.user.application.service.LoginService;
import com.guttery.madii.domain.user.application.service.LogoutService;
import com.guttery.madii.domain.user.application.service.ProfileService;
import com.guttery.madii.domain.user.application.service.SignUpService;
import com.guttery.madii.domain.user.application.service.WithdrawService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final ProfileService profileService;
    private final WithdrawService withdrawService;
    private final LogoutService logoutService;

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
    @Operation(summary = "ID 중복 체크 API", description = "ID 중복 체크 API입니다. true인 경우 회원가입 가능, false인 경우 불가능을 나타냅니다.")
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
    @Operation(summary = "회원가입 API", description = "회원가입 API입니다.")
    public LoginResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
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
    @Operation(summary = "일반 로그인 API", description = "일반 로그인 API입니다.")
    public LoginResponse normalLogin(@Valid @RequestBody NormalLoginRequest normalLoginRequest) {
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
    @Operation(summary = "카카오 로그인 API", description = "카카오 로그인 API입니다.")
    public LoginResponse kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
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
    @Operation(summary = "애플 로그인 API", description = "애플 로그인 API입니다.")
    public LoginResponse appleLogin(@Valid @RequestBody AppleLoginRequest appleLoginRequest) {
        return loginService.appleLogin(appleLoginRequest);
    }

    @PutMapping("/marketing-agreement")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "마케팅 수신 동의 변경 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "마케팅 수신 동의 변경 API", description = "마케팅 수신 동의 변경 API입니다.")
    public void updateMarketingAgreement(
            @Valid @RequestBody UpdateMarketingAgreementRequest updateMarketingAgreementRequest,
            @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        profileService.updateMarketingAgreement(updateMarketingAgreementRequest, userPrincipal);
    }

    @PostMapping("/refresh")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토큰 리프레시 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "토큰 리프레시 API", description = "토큰 리프레시 API입니다.")
    public RefreshResponse refresh(
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest
    ) {
        return loginService.refresh(tokenRefreshRequest);
    }

    @GetMapping("/profile")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로필 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "프로필 조회 API", description = "프로필 조회 API입니다.")
    public ProfileReadResponse readProfile(
            @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        return profileService.readProfile(userPrincipal);
    }

    @PostMapping("/profile")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "프로필 수정 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "프로필 등록 및 수정 API", description = "프로필 등록 및 수정 API입니다.")
    public void updateProfile(
            @Valid @RequestBody ProfileUpdateRequest profileUpdateRequest,
            @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        profileService.updateProfile(profileUpdateRequest, userPrincipal);
    }

    @DeleteMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 탈퇴 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API입니다.")
    public void withdraw(
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        withdrawService.withdraw(userPrincipal);
    }

    @DeleteMapping("/logout")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그아웃 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "로그아웃 API", description = "로그아웃 API입니다.")
    public void logout(
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal,
            @Valid @RequestBody final LogoutRequest logoutRequest
    ) {
        logoutService.logout(logoutRequest, userPrincipal);
    }
}
