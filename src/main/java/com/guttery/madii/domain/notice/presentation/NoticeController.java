package com.guttery.madii.domain.notice.presentation;

import com.guttery.madii.domain.notice.application.dto.CreateNoticeRequest;
import com.guttery.madii.domain.notice.application.dto.RecentNoticesResponse;
import com.guttery.madii.domain.notice.application.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/notices")
@Validated
@Tag(name = "Notice", description = "공지사항 관련 API")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "최근 30일간 공지사항 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "최근 30일간 공지사항 조회 API", description = "최근 30일간 공지사항 조회 API입니다.")
    public RecentNoticesResponse getRecentNotices() {
        return noticeService.getRecentNotices();
    }

    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "공지사항 생성 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "공지사항 생성 API", description = "테스트용 공지사항 생성 API입니다.")
    public void createNotice(@Valid @RequestBody CreateNoticeRequest createNoticeRequest) {
        noticeService.createNotice(createNoticeRequest);
    }
}
