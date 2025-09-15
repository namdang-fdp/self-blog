package com.namdang.blog.repositories;

import com.namdang.blog.entities.GenresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepositoy extends JpaRepository<GenresEntity, UUID> {
    Optional<GenresEntity> findByNameIgnoreCase(String name);
}
