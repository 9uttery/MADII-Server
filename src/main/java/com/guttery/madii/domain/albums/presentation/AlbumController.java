package com.guttery.madii.domain.albums.presentation;

import com.guttery.madii.domain.albums.application.dto.AlbumCreateRequest;
import com.guttery.madii.domain.albums.application.dto.AlbumCreateResponse;
import com.guttery.madii.domain.albums.application.service.AlbumService;
import com.guttery.madii.domain.user.domain.model.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
