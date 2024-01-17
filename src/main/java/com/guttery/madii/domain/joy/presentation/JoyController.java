package com.guttery.madii.domain.joy.presentation;

import com.guttery.madii.domain.joy.application.dto.JoyCreateRequest;
import com.guttery.madii.domain.joy.application.dto.JoyCreateResponse;
import com.guttery.madii.domain.joy.application.dto.JoyGetMyAllResponse;
import com.guttery.madii.domain.joy.application.service.JoyService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/joy")
@Validated
@Tag(name = "Joy", description = "Joy 관련 API")
public class JoyController {
    private final JoyService joyService;

    @PostMapping("")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "소확행 기록 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "소확행 기록 API", description = "소확행 기록 API입니다.")
    public JoyCreateResponse createJoy(@Valid @RequestBody JoyCreateRequest joyCreateRequest,
                                       @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return joyService.createJoy(joyCreateRequest, userPrincipal);
    }

    @GetMapping("")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "내가 기록한 소확행 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "내가 기록한 소확행 조회 API", description = "내가 기록한 소확행 조회 API입니다.")
    public List<JoyGetMyAllResponse> getMyJoy(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return joyService.getMyJoy(userPrincipal);
    }
}
