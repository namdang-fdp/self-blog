package com.namdang.blog.services;

import com.namdang.blog.dto.request.BlogRequest;
import com.namdang.blog.dto.response.BlogResponse;
import com.namdang.blog.dto.response.GenreResponse;
import com.namdang.blog.entities.BlogEntity;
import com.namdang.blog.entities.GenresEntity;
import com.namdang.blog.entities.UserEntity;
import com.namdang.blog.exception.AppException;
import com.namdang.blog.exception.ErrorCode;
import com.namdang.blog.repositories.BlogRepository;
import com.namdang.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService {
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final GenreService genreService;
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

    public void createBlog(BlogRequest blogRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<GenresEntity> genres = new ArrayList<>();
        if(blogRequest.getGenres() != null) {
            for(String genre : blogRequest.getGenres()) {
                genres.add(genreService.findOrCreate(genre));
            }
        }

        String coverPath  = null;
        if(blogRequest.getCoverPath() != null && !blogRequest.getCoverPath().isEmpty()) {
            coverPath = s3Service.upload("blog-covers", UUID.randomUUID().toString(), blogRequest.getCoverPath());
        }

        BlogEntity blog = modelMapper.map(blogRequest, BlogEntity.class);
        blog.setPostedUser(user);
        blog.setGenres(genres);
        blog.setCoverPath(coverPath);
        blog.setPublicationDate(new Date());
        blog.setView(0);
        blog.setTotalReact(0);

        blogRepository.save(blog);
    }

    public BlogResponse getBlogById(UUID id) {
        BlogEntity blogEntity = blogRepository.findByBlogId(id)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
        return

    }
}
