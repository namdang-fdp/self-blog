package com.namdang.blog.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {
    private UUID id;
    private String name;
}
