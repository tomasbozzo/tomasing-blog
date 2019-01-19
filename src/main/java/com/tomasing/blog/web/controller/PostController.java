package com.tomasing.blog.web.controller;

import com.tomasing.blog.service.PostService;
import com.tomasing.blog.service.model.Post;
import com.tomasing.blog.web.exception.NotFoundException;
import com.tomasing.blog.web.model.PostResource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public Resources<PostResource> getPosts() {
        Stream<PostResource> posts = postService.findAll()
                .map(this::toPostResponse);

        return new Resources<>(posts.collect(Collectors.toList()), linkTo(methodOn(PostController.class).getPosts()).withSelfRel());
    }

    @GetMapping(value = "/categories/{category}/posts/{slug}")
    public HttpEntity<PostResource> getPost(@PathVariable String category, @PathVariable String slug) {
        PostResource post = postService.findByCategoryAndSlug(category, slug)
                .map(this::toPostResponse)
                .orElseThrow(NotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.add("cache-control", "max-age=604800"); // One week
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
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

        result.add(linkTo(methodOn(PostController.class).getPost(post.getCategory(), post.getSlug()))
                .withSelfRel());

        return result;
    }
}
