package com.tomasing.blog.web.controller;

import com.tomasing.blog.service.PostService;
import com.tomasing.blog.service.model.Post;
import com.tomasing.blog.web.exception.NotFoundException;
import com.tomasing.blog.web.model.PostResource;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public Resources<PostResource> findAll() {
        Stream<PostResource> posts = postService.findAll()
                .map(this::toPostResponse);

        return new Resources<>(posts.collect(toList()), linkTo(methodOn(PostController.class).findAll()).withSelfRel());
    }

    @GetMapping(value = "/categories/{category}/posts/{slug}")
    public HttpEntity<PostResource> findByCategoryAndSlug(@PathVariable String category, @PathVariable String slug) {
        PostResource post = postService.findByCategoryAndSlug(category, slug)
                .map(this::toPostResponse)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Post not found: /{0}/{1}", category, slug)));

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(7, TimeUnit.DAYS))
                .body(post);
    }

    private PostResource toPostResponse(Post post) {
        PostResource result = PostResource.builder()
                .category(post.getCategory())
                .content(post.getContent())
                .title(post.getTitle())
                .slug(post.getSlug())
                .publishedBy(post.getPublishedBy())
                .publicationDate(post.getPublicationDate())
                .build();

        result.add(linkTo(methodOn(PostController.class).findByCategoryAndSlug(post.getCategory(), post.getSlug()))
                .withSelfRel());

        return result;
    }
}
