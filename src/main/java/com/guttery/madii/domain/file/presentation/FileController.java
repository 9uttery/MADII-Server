package com.guttery.madii.domain.file.presentation;

import com.guttery.madii.domain.file.application.dto.FileUploadResponse;
import com.guttery.madii.domain.file.application.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
@Validated
@Tag(name = "File", description = "파일 관련 API")
public class FileController {
    private final FileUploadService fileUploadService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "S3에 파일 업로드 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "파일 업로드", description = "파일을 업로드하고 URL을 받아오는 API입니다.")
    public FileUploadResponse uploadFile(
            @Parameter(
                    description = "multipart/form-data 형식의 단일 이미지를 입력 값으로 받습니다. 이때 key 값은 image입니다.",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart("image") final MultipartFile multipartFile) {
        return fileUploadService.uploadFileFromMultipartFile(multipartFile);
    }
}
