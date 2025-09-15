package com.namdang.blog.repositories;

import com.namdang.blog.entities.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<BlogEntity, UUID> {

    Optional<BlogEntity> findByBlogId(UUID blogId);
}
