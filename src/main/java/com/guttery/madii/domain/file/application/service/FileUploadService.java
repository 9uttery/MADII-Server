package com.guttery.madii.domain.file.application.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import com.guttery.madii.domain.file.application.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileUploadService {
    private static final String FILE_NAME_DELIMITER = "_";
    private static final String FILE_LINK_DELIMITER = "/";

    private final AmazonS3 ncpObjectStorageClient;

    @Value("${ncp.config.bucket-name}")
    private String bucketName;
    @Value("${ncp.config.cdn-url}")
    private String cdnUrl;

    public FileUploadResponse uploadFileFromMultipartFile(final MultipartFile multipartFile) {

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw CustomException.of(ErrorDetails.INVALID_FILE);
        }

        final String fileName = multipartFile.getOriginalFilename() + FILE_NAME_DELIMITER + System.currentTimeMillis();

        try {
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());
            PutObjectRequest putObjectRequest = new com.amazonaws.services.s3.model.PutObjectRequest(
                    bucketName,
                    fileName,
                    multipartFile.getInputStream(),
                    metadata
            );
            ncpObjectStorageClient.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (final IOException e) {
            log.error("파일 업로드 실패: {}", e.getMessage());
            throw CustomException.of(ErrorDetails.FILE_UPLOAD_FAILED);
        }

        return new FileUploadResponse(cdnUrl + bucketName + FILE_LINK_DELIMITER + fileName);
    }
}
