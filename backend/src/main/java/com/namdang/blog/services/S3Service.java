package com.namdang.blog.services;

import com.namdang.blog.exception.AppException;
import com.namdang.blog.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
public class S3Service {

    @Value("${s3.bucket}")
    private String bucketName;

    @Value("${s3.prefix}")
    private String prefix;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private CompressionService compressionService;

    public String upload(String key, File file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
            return prefix + key;
        } catch (S3Exception e) {
            log.error("S3 upload failed: {}", e.getMessage(), e);
            return null;
        }
    }

    public String upload(String keyPrefix, String id, MultipartFile multipartFile) {
        File tempFile = null;
        try {
            multipartFile = compressionService.compress(multipartFile);
            tempFile = File.createTempFile("temp-", multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempFile);
            String key = String.format("%s/%s.%s",
                    keyPrefix,
                    id,
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename())
            );
            String path = upload(key, tempFile);
            return path;
        } catch (Exception e) {
            log.error("Upload failed: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                boolean deleted = tempFile.delete();
                if (!deleted) log.warn("Temp file {} not deleted", tempFile.getAbsolutePath());
            }
        }
    }
    public InputStream download(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            return s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            log.error("S3 download failed: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
