package com.tomasing.blog.service;

import com.tomasing.blog.service.model.Post;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * The business layer class for posts.
 */
public interface PostService {

    /**
     * Gets all the posts.
     * @return All the posts.
     */
    Stream<Post> findAll();

    /**
     * Gets a specific post by its category and slug.
     *
     * @param category The post category.
     * @param slug The post slug.
     * @return The post which category and slug match the specified parameters or an empty optional.
     */
    Optional<Post> findByCategoryAndSlug(String category, String slug);
}
