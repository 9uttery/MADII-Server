package com.guttery.madii.domain.notification.presentation;

import com.guttery.madii.domain.notification.application.dto.NotificationListResponse;
import com.guttery.madii.domain.notification.application.dto.SaveTokenRequest;
import com.guttery.madii.domain.notification.application.service.NotificationQueryService;
import com.guttery.madii.domain.notification.application.service.NotificationTokenService;
import com.guttery.madii.domain.notification.application.service.TodayJoyNotificationSendService;
import com.guttery.madii.domain.notification.application.service.UnfinishedAchievementNotificationSendService;
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
    private final NotificationTokenService notificationTokenService;
    private final NotificationQueryService notificationQueryService;
    private final TodayJoyNotificationSendService todayJoyNotificationSendService;
    private final UnfinishedAchievementNotificationSendService unfinishedAchievementNotificationSendService;

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
    public void saveNotificationToken(
            @RequestBody @Valid SaveTokenRequest request,
            @NotNull @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        notificationTokenService.saveNotificationToken(request, userPrincipal);
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
        todayJoyNotificationSendService.sendAndSaveTodayJoyNotifications();
    }


    @PostMapping("/test/playlist")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "플레이리스트 알림 발송 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "플레이리스트 미완료 알림 발송 테스트 API", description = "플레이리스트 미완료 알림 발송 테스트 API입니다.")
    public void sendPlaylistNotification() {
        unfinishedAchievementNotificationSendService.sendUnfinishedAchievementNotifications();
    }
}
