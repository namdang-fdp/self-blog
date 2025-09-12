package com.namdang.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BlogResponse {
    private UUID blogId;
    private UserResponse postedUser;
    private String title;
    private String summary;
    private String content;
    private String coverPath;
    private List<GenreResponse> genres;
    public Date publicationDate;
    public int view;
    public int totalReact;
}
