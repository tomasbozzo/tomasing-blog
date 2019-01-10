package com.tomasing.blog.repository;

import com.tomasing.blog.repository.model.PostEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
    Optional<PostEntity> findByCategoryAndSlug(String category, String slug);
}
