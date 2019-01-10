package com.tomasing.blog.web.controller;

import com.tomasing.blog.service.PostService;
import com.tomasing.blog.service.model.Post;
import com.tomasing.blog.web.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{category}/{slug}")
    public String getPost(@PathVariable String category, @PathVariable String slug, Model model) {
        Post post = postService.findByCategoryAndSlug(category, slug)
                .orElseThrow(NotFoundException::new);

        model.addAttribute("post", post);

        return "post";
    }
}
