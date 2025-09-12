package com.namdang.blog.services;

import com.namdang.blog.common.ByteArrayMultipartFile;
import com.namdang.blog.exception.AppException;
import com.namdang.blog.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CompressionService {
    @Value("${server.compression.image-size:1280}")
    private int IMAGE_SIZE;

    private MultipartFile replace(MultipartFile file, byte[] imageContent) {
        try {
            return new ByteArrayMultipartFile(
                    file.getName(),
                    file.getOriginalFilename(),
                    file.getContentType(),
                    imageContent
            );
        } catch (Exception e) {
            log.error("Error when replace byte[] images to MultipartFile: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
