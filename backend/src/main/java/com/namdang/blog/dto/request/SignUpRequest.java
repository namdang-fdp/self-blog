package com.namdang.blog.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String quote;
    private String avatar;
    private Date dob;
    private String role;
}
