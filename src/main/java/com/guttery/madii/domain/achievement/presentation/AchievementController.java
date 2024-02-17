package com.guttery.madii.domain.achievement.presentation;

import com.guttery.madii.domain.achievement.application.dto.AddAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.CancelAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.FinishAchievementRequest;
import com.guttery.madii.domain.achievement.application.dto.JoyPlaylistResponse;
import com.guttery.madii.domain.achievement.application.service.JoyPlaylistService;
import com.guttery.madii.domain.achievement.application.service.UpdateAchievementStatusService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/achievements")
@Validated
@Tag(name = "Achievement", description = "실천 및 캘린더 관련 API")
public class AchievementController {
    private final JoyPlaylistService joyPlaylistService;
    private final UpdateAchievementStatusService updateAchievementStatusService;

    @GetMapping
    public JoyPlaylistResponse getAchievementsInPlaylist(
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        return joyPlaylistService.getAchievementsInPlaylist(userPrincipal);
    }

    @PostMapping
    public void addAchievementInPlaylist(
            @Valid @RequestBody final AddAchievementRequest addAchievementRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        joyPlaylistService.addAchievementInPlaylist(addAchievementRequest, userPrincipal);
    }

    @PutMapping("/finish")
    public void finishAchievement(
            @Valid @RequestBody final FinishAchievementRequest finishAchievementRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        updateAchievementStatusService.finishAchievement(finishAchievementRequest, userPrincipal);
    }

    @PutMapping("/cancel")
    public void cancelAchievement(
            @Valid @RequestBody final CancelAchievementRequest cancelAchievementRequest,
            @NotNull @AuthenticationPrincipal final UserPrincipal userPrincipal
    ) {
        updateAchievementStatusService.cancelAchievement(cancelAchievementRequest, userPrincipal);
    }

    
}
