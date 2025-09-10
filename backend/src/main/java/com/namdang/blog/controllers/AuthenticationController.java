package com.namdang.blog.controllers;

import com.namdang.blog.dto.response.ApiResponse;
import com.namdang.blog.dto.response.AuthenticationResponse;
import com.namdang.blog.dto.request.SignInRequest;
import com.namdang.blog.dto.request.SignUpRequest;
import com.namdang.blog.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Register successfully");
        apiResponse.setResult(authenticationService.signUp(signUpRequest));
        return apiResponse;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInRequest signInRequest,
                                   HttpServletResponse response) {

        String token = authenticationService.authenticateAndGenerateToken(signInRequest);
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Lax")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok("Login successful");
    }
}