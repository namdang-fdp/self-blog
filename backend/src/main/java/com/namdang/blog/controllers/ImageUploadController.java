package com.namdang.blog.controllers;

import com.namdang.blog.services.ImageUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/images")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }
            String fileId = UUID.randomUUID().toString();
            String folder = "test";
            String url = imageUploadService.uploadImage(folder, fileId, file);
            if (url == null) {
                return ResponseEntity.internalServerError().body("Upload failed");
            }
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            log.error("Upload API error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error uploading file");
        }
    }
}
