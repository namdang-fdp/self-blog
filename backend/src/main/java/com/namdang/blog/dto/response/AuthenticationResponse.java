package com.namdang.blog.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public static class Builder {
        private String token;
        private UserResponse user;

        public Builder token(String token) {
            this.token = token;
            return this;
        }
        public AuthenticationResponse build() {
            return new AuthenticationResponse(token);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
}
