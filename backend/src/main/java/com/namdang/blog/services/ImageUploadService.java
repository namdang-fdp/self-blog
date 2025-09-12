package com.namdang.blog.services;

import com.namdang.blog.exception.AppException;
import com.namdang.blog.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageUploadService {

    @Autowired
    private S3Service s3Service;

    public String uploadImage(String folder, String id, MultipartFile file) {
        try {
            return s3Service.upload(folder, id, file);
        } catch (Exception e) {
            log.error("Image upload failed: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);

        }
    }
}
