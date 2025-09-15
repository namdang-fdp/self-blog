package com.namdang.blog.controllers;


import com.namdang.blog.dto.GenreDTO;
import com.namdang.blog.dto.response.ApiResponse;
import com.namdang.blog.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenresController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public ApiResponse<List<GenreDTO>> getAllGenres() {
        ApiResponse<List<GenreDTO>> response = new ApiResponse<>();
        response.setMessage("Get all genres successfully");
        response.setResult(genreService.getAllGenre());
        return response;
    }

}
