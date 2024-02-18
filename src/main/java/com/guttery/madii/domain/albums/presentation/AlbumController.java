package com.guttery.madii.domain.albums.presentation;

import com.guttery.madii.domain.albums.application.dto.*;
import com.guttery.madii.domain.albums.application.service.AlbumService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
@Validated
@Tag(name = "Albums", description = "Albums 관련 API")
public class AlbumController {
    private final AlbumService albumService;

    @PostMapping("")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "새로운 앨범 생성 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "새로운 앨범 생성 API", description = "새로운 앨범 생성 API입니다.")
    public List<AlbumCreateResponse> createAlbum(@Valid @RequestBody AlbumCreateRequest albumCreateRequest,
                                                 @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return albumService.createAlbum(albumCreateRequest, userPrincipal);
    }

    @PostMapping("/{joyId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범에 소확행 추가 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범에 소확행 추가 API", description = "앨범에 소확행 추가 API입니다.")
    public void addJoyToAlbum(@PathVariable Long joyId,
                              @Valid @RequestBody AlbumSaveJoyRequest albumSaveJoyRequest,
                              @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.addJoyToAlbum(joyId, albumSaveJoyRequest, userPrincipal);
    }

    @PutMapping("/{albumId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범 이름, 설명 수정 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범 이름, 설명 수정 API", description = "앨범 이름, 설명 수정 API입니다.")
    public void putMyAlbum(@PathVariable Long albumId,
                            @Valid @RequestBody AlbumPutRequest albumPutRequest,
                            @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.putMyAlbum(albumId, albumPutRequest, userPrincipal);
    }

    @PutMapping("/{albumId}/status")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범 공개 여부 수정 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범 공개 여부 수정 API", description = "앨범 공개 여부 수정 API입니다.")
    public void putMyAlbumStatus(@PathVariable Long albumId,
                                 @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.putMyAlbumStatus(albumId, userPrincipal);
    }

    @GetMapping("/{albumId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범 상세 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범 상세 조회 API", description = "앨범 상세 조회 API입니다.")
    public AlbumGetDetailResponse getAlbumDetail(@PathVariable Long albumId,
                                                 @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return albumService.getAlbumDetail(albumId, userPrincipal);
    }

    @PostMapping("/{albumId}/bookmarks")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범 북마크 등록 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범 북마크 등록 API", description = "앨범 북마크 등록 API입니다.")
    public void createAlbumBookmark(@PathVariable Long albumId,
                                    @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.createAlbumBookmark(albumId, userPrincipal);
    }

    @DeleteMapping("/{albumId}/bookmarks")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범 북마크 해제 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범 북마크 해제 API", description = "앨범 북마크 해제 API입니다.")
    public void deleteAlbumBookmark(@PathVariable Long albumId,
                                    @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.deleteAlbumBookmark(albumId, userPrincipal);
    }

    @GetMapping("")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "내가 만든 & 저장한 앨범 목록 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "내가 만든 & 저장한 앨범 목록 조회 API", description = "내가 만든 & 저장한 앨범 목록 조회 API입니다.")
    public List<AlbumGetMyAllResponse> getMyAllAlbums(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return albumService.getMyAllAlbums(userPrincipal);
    }

    @GetMapping("/created")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "내가 만든 앨범 목록 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "내가 만든 앨범 목록 조회 API", description = "내가 만든 앨범 목록 조회 API입니다.")
    public List<AlbumGetCreatedResponse> getCreatedAlbums(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return albumService.getCreatedAlbums(userPrincipal);
    }

    @GetMapping("/joy/{joyId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "특정 소확행 저장 여부와 함께 앨범 목록 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "특정 소확행 저장 여부와 함께 앨범 목록 조회 API", description = "특정 소확행 저장 여부와 함께 앨범 목록 조회 API입니다.")
    public List<AlbumGetJoyAllResponse> getMyJoyAllAlbums(@PathVariable Long joyId,
                                                          @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return albumService.getMyJoyAllAlbums(joyId, userPrincipal);
    }

    @GetMapping("/all")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "전체 앨범 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "전체 앨범 조회 API", description = "전체 앨범 조회 API입니다.")
    public Slice<AlbumGetAllResponse> getAllAlbums(@RequestParam(required = false) Long albumId,
                                                   @RequestParam int size) {
        return albumService.getAllAlbums(albumId, size);
    }

    @GetMapping("/{albumId}/random")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "다른 소확행 앨범 모음 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "다른 소확행 앨범 모음 조회 API", description = "다른 소확행 앨범 모음 조회 API입니다.")
    public List<AlbumGetOthersResponse> getOtherAlbums(@PathVariable Long albumId,
                                                       @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return albumService.getOtherAlbums(albumId, userPrincipal);
    }

    @PostMapping("/recent/{albumId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "최근 본 소확행 앨범 등록 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "최근 본 소확행 앨범 등록 API", description = "최근 본 소확행 앨범 등록 API입니다.")
    public void createRecentAlbums(@PathVariable Long albumId,
                                   @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.createRecentAlbums(albumId, userPrincipal);
    }

    @GetMapping("/recent")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "최근 본 소확행 앨범 등록 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "최근 본 소확행 앨범 조회 API", description = "최근 본 소확행 앨범 조회 API입니다.")
    public List<AlbumGetRecentResponse> getRecentAlbums(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return albumService.getRecentAlbums(userPrincipal);
    }

    @DeleteMapping("/{albumId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범 삭제 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범 삭제 API", description = "앨범 삭제 API입니다.")
    public void deleteAlbum(@PathVariable Long albumId,
                            @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.deleteAlbum(albumId, userPrincipal);
    }

    @PostMapping("/{albumId}/report")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "앨범 신고 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "앨범 신고 API", description = "앨범 신고 API입니다.")
    public void reportAlbum(@Valid @RequestBody AlbumReportRequest albumReportRequest,
                            @PathVariable Long albumId,
                            @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        albumService.reportAlbum(albumReportRequest, albumId, userPrincipal);
    }

}
