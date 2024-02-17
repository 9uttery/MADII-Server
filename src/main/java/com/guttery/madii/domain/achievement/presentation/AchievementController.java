package com.guttery.madii.domain.achievement.presentation;

import com.guttery.madii.domain.achievement.application.dto.AddAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.CalenderAchievementColorResponse;
import com.guttery.madii.domain.achievement.application.dto.CancelAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.FinishAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;
import com.guttery.madii.domain.achievement.application.dto.MoveAchievementToTodayRequest;
import com.guttery.madii.domain.achievement.application.dto.RateAchievementRequest;
import com.guttery.madii.domain.achievement.application.service.CalenderService;
import com.guttery.madii.domain.achievement.application.service.JoyPlaylistService;
import com.guttery.madii.domain.achievement.application.service.UpdateAchievementStatusService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/achievements")
@Validated
@Tag(name = "Achievement", description = "실천 및 캘린더 관련 API")
public class AchievementController {
    private final JoyPlaylistService joyPlaylistService;
    private final UpdateAchievementStatusService updateAchievementStatusService;
    private final CalenderService calenderService;

    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "플레이리스트 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "플레이리스트 조회 API", description = "오플리 조회 API입니다.")
    public JoyPlaylistResponse getAchievementsInPlaylist(
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "플레이리스트 추가 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "플레이리스트 추가 API", description = "오플리에 실천을 추가하는 API입니다.")
    public JoyPlaylistResponse addAchievementInPlaylist(
            @Valid @RequestBody final AddAchievementRequest addAchievementRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        joyPlaylistService.addAchievementInPlaylist(addAchievementRequest, userPrincipal);

        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @DeleteMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "플레이리스트 삭제 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "플레이리스트 삭제 API", description = "오플리에서 실천을 삭제하는 API입니다.")
    public JoyPlaylistResponse deleteAchievementInPlaylist(
            @NotNull @RequestParam final Long achievementId,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        joyPlaylistService.deleteAchievementInPlaylist(achievementId, userPrincipal);

        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @PutMapping("/finish")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "실천 완료 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "실천 완료 API", description = "실천 완료 API입니다. 만족도 조사를 위한 satisfaction은 BAD, SO_SO, GOOD, GREAT, EXCELLENT 중 하나여야 합니다.")
    public JoyPlaylistResponse finishAchievement(
            @Valid @RequestBody final FinishAchievementRequest finishAchievementRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        updateAchievementStatusService.finishAchievement(finishAchievementRequest, userPrincipal);

        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @PutMapping("/rate")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "실천 만족도 수정 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "실천 만족도 수정 API", description = "실천 만족도 수정 API입니다. 만족도 조사를 위한 satisfaction은 BAD, SO_SO, GOOD, GREAT, EXCELLENT 중 하나여야 합니다.")
    public JoyPlaylistResponse rateAchievement(
            @Valid @RequestBody final RateAchievementRequest rateAchievementRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        updateAchievementStatusService.rateAchievement(rateAchievementRequest, userPrincipal);

        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @PutMapping("/cancel")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "실천 취소 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "실천 취소 API", description = "실천 취소 API입니다.")
    public JoyPlaylistResponse cancelAchievement(
            @Valid @RequestBody final CancelAchievementRequest cancelAchievementRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        updateAchievementStatusService.cancelAchievement(cancelAchievementRequest, userPrincipal);

        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @PutMapping("/move")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "실천 이동 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "어제 못한 실천 오늘로 이동 API", description = "어제 못한 실천 오늘로 이동 API입니다.")
    public JoyPlaylistResponse moveAchievement(
            @Valid @RequestBody final MoveAchievementToTodayRequest moveAchievementToTodayRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        joyPlaylistService.moveAchievementInPlaylist(moveAchievementToTodayRequest, userPrincipal);

        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @GetMapping("/calender")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "캘린더 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "특정 기간 캘린더 조회 API", description = "특정 기간 캘린더 조회 API입니다.")
    public CalenderAchievementColorResponse getAchievementsInCalendar(
            @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate startDate,
            @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endDate,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        return calenderService.getAchievementColorInfos(startDate, endDate, userPrincipal);
    }
}
