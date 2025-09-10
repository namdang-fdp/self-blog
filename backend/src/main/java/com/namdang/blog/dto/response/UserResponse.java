package com.namdang.blog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String quote;
    private String avatar;
    private String role;
    private Date dob;
}
