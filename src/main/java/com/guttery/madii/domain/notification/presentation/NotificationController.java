package com.guttery.madii.domain.notification.presentation;

import com.guttery.madii.domain.notification.application.dto.NotificationListResponse;
import com.guttery.madii.domain.notification.application.dto.SaveTokenRequest;
import com.guttery.madii.domain.notification.application.service.NotificationQueryService;
import com.guttery.madii.domain.notification.application.service.NotificationSendService;
import com.guttery.madii.domain.notification.application.service.UserTokenService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
@Validated
@Tag(name = "Notification", description = "Notification 관련 API입니다.")
public class NotificationController {
    private final UserTokenService userTokenService;
    private final NotificationQueryService notificationQueryService;
    private final NotificationSendService notificationSendService;

    @PostMapping("/token")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "FCM 토큰 저장 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "FCM 토큰 저장 API", description = "FCM 토큰을 저장하는 API입니다.")
    public void saveUserToken(
            @RequestBody @Valid SaveTokenRequest request,
            @NotNull @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        userTokenService.saveUserToken(request, userPrincipal);
    }

    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "알림 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "알림 조회 API", description = "알림 조회 API입니다.")
    public NotificationListResponse getNotificationList(
            @NotNull @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return notificationQueryService.getNotificationList(userPrincipal);
    }

    @PostMapping("/test/today")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "오늘의 소확행 알림 발송 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "오늘의 소확행 알림 발송 테스트 API", description = "오늘의 소확행 알림 발송 테스트 API입니다.")
    public void sendNotification() {
        notificationSendService.sendAndSaveTodayJoyNotifications();
    }
}
