package com.guttery.madii.domain.placeholder.presentation;

import com.guttery.madii.domain.placeholder.application.dto.PlaceholderResponse;
import com.guttery.madii.domain.placeholder.application.dto.UpdatePlaceholderRequest;
import com.guttery.madii.domain.placeholder.application.service.PlaceholderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/placeholders")
@Validated
@Tag(name = "플레이스홀더", description = "플레이스홀더 관리 API")
public class PlaceholderController {
    private final PlaceholderService placeholderService;

    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "플레이스홀더 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "플레이스홀더 조회 API", description = "플레이스홀더 조회 API입니다.")
    public PlaceholderResponse getPlaceholder() {
        return placeholderService.getPlaceholder();
    }

    @PutMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "플레이스홀더 수정 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "플레이스홀더 수정 API", description = "플레이스홀더 수정 API입니다.")
    public void updatePlaceholder(@NotNull @RequestBody UpdatePlaceholderRequest updatePlaceholderRequest) {
        placeholderService.updatePlaceholder(updatePlaceholderRequest);
    }
}
