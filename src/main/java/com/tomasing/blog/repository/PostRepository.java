package com.tomasing.blog.repository;

import com.tomasing.blog.repository.model.PostEntity;

import java.util.Optional;

public interface PostRepository {
    Optional<PostEntity> findByCategoryAndSlug(String category, String slug);
    Iterable<PostEntity> findAll();
}
