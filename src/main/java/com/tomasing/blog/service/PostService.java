package com.tomasing.blog.service;

import com.tomasing.blog.service.model.Post;

import java.util.stream.Stream;

public interface PostService {
    Stream<Post> getPosts();
}
