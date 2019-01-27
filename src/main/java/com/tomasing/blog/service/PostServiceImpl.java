package com.tomasing.blog.service;

import com.tomasing.blog.repository.PostRepository;
import com.tomasing.blog.service.mappers.PostMapper;
import com.tomasing.blog.service.model.Post;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Stream<Post> findAll() {
        return postRepository.findAll()
                .map(PostMapper::toPost);
    }

    @Override
    public Optional<Post> findByCategoryAndSlug(String category, String slug) {
        return postRepository.findByCategoryAndSlug(category, slug)
                .map(PostMapper::toPost);
    }
}
