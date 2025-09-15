package com.namdang.blog.services;

import com.namdang.blog.dto.GenreDTO;
import com.namdang.blog.entities.GenresEntity;
import com.namdang.blog.repositories.GenreRepositoy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    public final GenreRepositoy genreRepositoy;
    public final ModelMapper modelMapper;

    public List<GenreDTO> getAllGenre() {
        return genreRepositoy.findAll().stream()
                .map(genresEntity -> modelMapper.map(genresEntity, GenreDTO.class))
                .toList();
    }

    @Transactional
    public GenresEntity findOrCreate(String name) {
        return genreRepositoy.findByNameIgnoreCase(name)
                .orElseGet(() -> genreRepositoy.save(
                        GenresEntity.builder().name(name).build()
                ));
    }

}
