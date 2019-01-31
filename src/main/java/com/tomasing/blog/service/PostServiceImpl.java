package com.tomasing.blog.service;

import com.tomasing.blog.repository.PostRepository;
import com.tomasing.blog.service.mappers.PostMapper;
import com.tomasing.blog.service.model.Post;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    private static final String EXCERPT_MARKER = "<!--more-->";

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Stream<Post> findAll() {
        return postRepository.findAll()
                .map(PostMapper::toPost)
                .map(this::stripToExcerpt);
    }

    @Override
    public Optional<Post> findByCategoryAndSlug(String category, String slug) {
        return postRepository.findByCategoryAndSlug(category, slug)
                .map(PostMapper::toPost);
    }

    private Post stripToExcerpt(Post post) {
        return Post.builder()
                .id(post.getId())
                .category(post.getCategory())
                .content(stripToExcerpt(post.getContent()))
                .publicationDate(post.getPublicationDate())
                .publishedBy(post.getPublishedBy())
                .slug(post.getSlug())
                .title(post.getTitle())
                .build();
    }

    private String stripToExcerpt(String content) {
        return StringUtils.substringBefore(content, EXCERPT_MARKER);
    }

}
