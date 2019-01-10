package com.tomasing.blog.web.controller;

import com.tomasing.blog.service.PostService;
import com.tomasing.blog.service.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    private static final String CATEGORY = "category";
    private static final String SLUG = "slug";
    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPost() throws Exception {
        // Given
        when(postService.findByCategoryAndSlug(CATEGORY, SLUG))
                .thenReturn(Optional.of(Post.builder().build()));

        // When
        mockMvc.perform(get("/category/slug"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPostNotFound() throws Exception {
        mockMvc.perform(get("/category/slug"))
                .andExpect(status().isNotFound());
    }
}