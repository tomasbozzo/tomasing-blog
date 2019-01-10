package com.tomasing.blog.web.controller;

import com.tomasing.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/")
public class HomeController {

    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("posts", postService.findAll().collect(toList()));
        return "home";
    }
}
