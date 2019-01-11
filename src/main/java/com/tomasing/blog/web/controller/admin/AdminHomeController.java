package com.tomasing.blog.web.controller.admin;

import com.tomasing.blog.service.PostService;
import com.tomasing.blog.service.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    private final PostService postService;

    public AdminHomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String get(Model model) {
        List<Post> posts = postService.findAll().collect(Collectors.toList());

        model.addAttribute("posts", posts);

        return "admin-home";
    }
}
