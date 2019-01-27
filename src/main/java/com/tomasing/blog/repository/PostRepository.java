package com.tomasing.blog.repository;

import com.tomasing.blog.repository.model.PostEntity;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The PostEntity repository.
 */
public interface PostRepository {

    /**
     * Finds a post by its category and slug.
     *
     * @param category The post category.
     * @param slug The post slug.
     * @return The found post or empty if it was not found.
     */
    Optional<PostEntity> findByCategoryAndSlug(@Nonnull String category, @Nonnull String slug);

    /**
     * Gets all the posts.
     *
     * @return All the posts.
     */
    @Nonnull
    Stream<PostEntity> findAll();
}
