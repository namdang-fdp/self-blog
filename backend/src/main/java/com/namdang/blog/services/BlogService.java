package com.namdang.blog.services;

import com.namdang.blog.dto.response.BlogResponse;
import com.namdang.blog.dto.response.GenreResponse;
import com.namdang.blog.repositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    public Page<BlogResponse> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable)
                .map(entity -> {
                    BlogResponse response = modelMapper.map(entity, BlogResponse.class);
                    response.setGenres(entity
                            .getGenres()
                            .stream()
                            .map(genre -> new GenreResponse(genre.getName()))
                            .toList()
                    );
                    return response;
                });
    }
}
