package com.namdang.blog.configs;

import com.namdang.blog.dto.request.SignUpRequest;
import com.namdang.blog.entities.UserEntity;
import com.namdang.blog.entities.enums.Role;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<SignUpRequest, UserEntity>() {
            @Override
            protected void configure() {
                skip(destination.getUserId());

                using(ctx -> Role.valueOf((String) ctx.getSource()))
                        .map(source.getRole(), destination.getRole());
            }
        });
        return modelMapper;
    }
}