package com.namdang.blog.controllers;

import com.namdang.blog.dto.response.ApiResponse;
import com.namdang.blog.dto.response.BlogResponse;
import com.namdang.blog.services.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/blogs")
    @Operation(description = "Get all blogs in the system (admin)")
    public ApiResponse<Page<BlogResponse>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogResponse> result = blogService.getAllBlogs(pageable);
        ApiResponse<Page<BlogResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get all blogs successfully");
        apiResponse.setResult(result);
        return apiResponse;
    }

}
