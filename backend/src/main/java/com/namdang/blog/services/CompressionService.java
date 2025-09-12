package com.namdang.blog.services;

import com.namdang.blog.common.ByteArrayMultipartFile;
import com.namdang.blog.exception.AppException;
import com.namdang.blog.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


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

    private MultipartFile compressImage(MultipartFile file) throws IOException {
        BufferedImage original = ImageIO.read(file.getInputStream());
        if(original == null) throw new AppException(ErrorCode.UNSUITABLE_IMAGE);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Thumbnails.of(original)
                .size(IMAGE_SIZE, IMAGE_SIZE)
                .keepAspectRatio(true)
                .outputFormat("jpg")
                .toOutputStream(output);
        byte[] content = output.toByteArray();
        val res = replace(file, content);
        return res;
    }

    public MultipartFile compress(MultipartFile file) {
        try {
            if(file.getContentType() != null && file.getContentType().startsWith("image/")) {
                return compressImage(file);
            }
            return file;
        } catch(Exception e) {
            log.error("Compressing image failed: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
