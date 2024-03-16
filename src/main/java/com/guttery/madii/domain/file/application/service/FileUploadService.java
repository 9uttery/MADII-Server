package com.guttery.madii.domain.file.application.service;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.file.application.dto.FileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Slf4j
@Service
public class FileUploadService {
    private static final String FILE_NAME_DELIMITER = "_";
    private static final String FILE_LINK_DELIMITER = "/";

    private final S3Client s3Client;

    private final String bucketName;
    private final String cloudFrontUrl;

    public FileUploadService(
            S3Client s3Client,
            @Value("${spring.cloud.aws.s3.bucket}") String bucketName,
            @Value("${spring.cdn.url}") String cloudFrontUrl
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.cloudFrontUrl = cloudFrontUrl;
    }

    public FileUploadResponse uploadFileFromMultipartFile(final MultipartFile multipartFile) {

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw CustomException.of(ErrorDetails.INVALID_FILE);
        }

        final String fileName = multipartFile.getOriginalFilename() + FILE_NAME_DELIMITER + System.currentTimeMillis();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .contentType(multipartFile.getContentType())
                    .contentLength(multipartFile.getSize())
                    .key(fileName)
                    .build();
            RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (final IOException e) {
            log.error("파일 업로드 실패: {}", e.getMessage());
            throw CustomException.of(ErrorDetails.FILE_UPLOAD_FAILED);
        }

        return new FileUploadResponse(cloudFrontUrl + FILE_LINK_DELIMITER + fileName);
    }
}
