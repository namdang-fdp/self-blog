package com.namdang.blog.services;

import com.namdang.blog.dto.response.AuthenticationResponse;
import com.namdang.blog.dto.request.SignInRequest;
import com.namdang.blog.dto.request.SignUpRequest;
import com.namdang.blog.dto.response.UserResponse;
import com.namdang.blog.entities.UserEntity;
import com.namdang.blog.exception.AppException;
import com.namdang.blog.exception.ErrorCode;
import com.namdang.blog.repositories.UserRepository;
import com.namdang.blog.security.JwtGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final ModelMapper modelMapper;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 JwtGenerator jwtGenerator,
                                 ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.modelMapper = modelMapper;
    }

    public AuthenticationResponse signUp(SignUpRequest signUpRequest){
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }

        UserEntity userEntity = modelMapper.map(signUpRequest, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(userEntity);

        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        return AuthenticationResponse.builder()
                .build();
    }

    public AuthenticationResponse signIn(SignInRequest signInRequest){
        UserEntity userFound = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!userFound.isEnabled()) {
            throw new AppException(ErrorCode.USER_NOT_ENABLE);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        UserResponse userResponse = modelMapper.map(userFound, UserResponse.class);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public String authenticateAndGenerateToken(SignInRequest signInRequest) {
        UserEntity userFound = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!userFound.isEnabled()) {
            throw new AppException(ErrorCode.USER_NOT_ENABLE);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateToken(authentication);
    }
}
