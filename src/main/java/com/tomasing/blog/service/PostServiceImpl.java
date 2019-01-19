package com.tomasing.blog.service;

import com.tomasing.blog.repository.PostRepository;
import com.tomasing.blog.repository.model.PostEntity;
import com.tomasing.blog.service.model.Post;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Stream<Post> findAll() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                .map(this::toPost);
    }

    @Override
    public Optional<Post> findByCategoryAndSlug(String category, String slug) {
        return postRepository.findByCategoryAndSlug(category, slug)
                .map(this::toPost);
    }

    private Post toPost(PostEntity post) {
        return Post.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .slug(post.getSlug())
                .publishedBy(post.getPublishedBy())
                .publicationDate(post.getPublicationDate())
                .build();
    }
}
