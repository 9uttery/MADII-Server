package com.guttery.madii.domain.notification.presentation;

import com.guttery.madii.domain.notification.application.dto.SaveTokenRequest;
import com.guttery.madii.domain.notification.application.service.UserTokenService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/token")
    public void saveUserToken(
            @RequestBody @Valid SaveTokenRequest request,
            @NotNull @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        userTokenService.saveUserToken(request, userPrincipal);
    }
}
